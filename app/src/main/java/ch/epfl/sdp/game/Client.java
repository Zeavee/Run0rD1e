package ch.epfl.sdp.game;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.database.firebase.api.ClientDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.EntityConverter;
import ch.epfl.sdp.database.firebase.entity.ItemBoxForFirebase;
import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.item.ItemBoxManager;

/**
 * This class updates the game from the client point of view. It fetches the data from firebase and
 * the data is updated by the server.
 */
public class Client implements Updatable {
    private static final String TAG = "Database";
    private int counter;
    private ClientDatabaseAPI clientDatabaseAPI;
    private PlayerManager playerManager = PlayerManager.getInstance();
    private EnemyManager enemyManager = EnemyManager.getInstance();
    private ItemBoxManager itemBoxManager = ItemBoxManager.getInstance();

    /**
     * Creates a new client
     */
    public Client(ClientDatabaseAPI clientDatabaseAPI) {
        this.clientDatabaseAPI = clientDatabaseAPI;

        // TODO Add Listener for and Players
        init();
        addEnemyListener();
        addItemBoxesListener();
        addUserHealthPointsListener();
        addUserItemListener();
    }

    @Override
    public void update() {
        if (counter <= 0) {
            sendUserPosition();
            sendUsedItems();
            counter = 2 * GameThread.FPS + 1;
        }

        --counter;
    }

    private void init() {
        clientDatabaseAPI.listenToGameStart(start -> {
            if (start.isSuccessful()) {
                Game.getInstance().addToUpdateList(this);
                Game.getInstance().initGame();
            }
        });
    }

    private void addEnemyListener() {
        clientDatabaseAPI.addEnemyListener(value -> {
            if (value.isSuccessful()) {
                for (Enemy enemy : EntityConverter.convertEnemyForFirebaseList(value.getResult())) {
                    enemyManager.updateEnemies(enemy);
                }
            }
        });
    }

    private void addItemBoxesListener() {
        // Listen for Items
        clientDatabaseAPI.addItemBoxesListener(value -> {
            if (value.isSuccessful()) {
                for (ItemBoxForFirebase itemBoxForFirebase : value.getResult()) {
                    String id = itemBoxForFirebase.getId();
                    boolean taken = itemBoxForFirebase.isTaken();
                    GeoPoint location = itemBoxForFirebase.getLocation();

                    if (itemBoxManager.getItemBoxes().containsKey(id)) {
                        if (taken) {
                            Game.getInstance().removeFromDisplayList(itemBoxManager.getItemBoxes().get(id));
                            itemBoxManager.getItemBoxes().remove(id);
                        }
                    } else {
                        if (!taken) {
                            ItemBox itemBox = new ItemBox(location);
                            Game.getInstance().addToDisplayList(itemBox);
                            itemBoxManager.addItemBoxWithId(itemBox, id);
                        }
                    }

                    Log.d(TAG, "Listen for itemboxes: " + value.getResult());
                }
            } else {
                Log.w(TAG, "Listen for itemBoxes failed.", value.getException());
            }
        });

    }

    private void addUserHealthPointsListener() {
        clientDatabaseAPI.addUserHealthPointsListener(value -> {
            if (value.isSuccessful()) {
                playerManager.getCurrentUser().setHealthPoints(value.getResult());
                Log.d(TAG, "Listen for healtpoints: " + value.getResult());
            } else {
                Log.w(TAG, "Listen for healtpoints failed.", value.getException());
            }
        });
    }

    private void addUserItemListener() {
        // Listen for Items
        clientDatabaseAPI.addUserItemListener(value -> {
            if (value.isSuccessful()) {
                Map<String, Integer> items = new HashMap<>();
                for (Map.Entry<String, Integer> entry : value.getResult().entrySet()) {
                    items.put(entry.getKey(), entry.getValue());
                }
                playerManager.getCurrentUser().getInventory().setItems(items);
                Log.d(TAG, "Listen for items: " + value.getResult());
            } else {
                Log.w(TAG, "Listen for items failed.", value.getException());
            }
        });
    }


    private void sendUserPosition() {
        clientDatabaseAPI.sendUserPosition(EntityConverter.playerToPlayerForFirebase(PlayerManager.getInstance().getCurrentUser()));
    }

    private void sendUsedItems() {
        Map<String, Integer> usedItems = playerManager.getCurrentUser().getInventory().getUsedItems();
        if (!usedItems.isEmpty()) {
            clientDatabaseAPI.sendUsedItems(EntityConverter.convertItems(usedItems));
            playerManager.getCurrentUser().getInventory().clearUsedItems();
        }
    }
}
