package ch.epfl.sdp.game.game_architecture;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.api.ClientDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.entityForFirebase.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.EntityConverter;
import ch.epfl.sdp.database.firebase.entityForFirebase.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.PlayerForFirebase;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.GameThread;
import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.utils.CustomResult;
import ch.epfl.sdp.entities.enemy.Enemy;
import ch.epfl.sdp.entities.enemy.EnemyManager;
import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.geometry.area.Area;
import ch.epfl.sdp.geometry.area.AreaFactory;
import ch.epfl.sdp.map.location.GeoPoint;
import ch.epfl.sdp.geometry.area.UnboundedArea;
import ch.epfl.sdp.items.item_box.ItemBox;
import ch.epfl.sdp.items.item_box.ItemBoxManager;

/**
 * This class updates the game from the client point of view. It fetches the data from firebase and
 * the data is updated by the server.
 */
public class Client extends StartGameController implements Updatable {
    private static final String TAG = "Database";
    private int counter = 0;
    private int counter10Sec = 0;
    private final ClientDatabaseAPI clientDatabaseAPI;
    private final CommonDatabaseAPI commonDatabaseAPI;
    private final PlayerManager playerManager = PlayerManager.getInstance();
    private final EnemyManager enemyManager = EnemyManager.getInstance();
    private final ItemBoxManager itemBoxManager = ItemBoxManager.getInstance();
    private Area area = new UnboundedArea();
    private boolean gameStarted;
    private long oldSignal;
    private long signal;
    private final Runnable endGame;

    /**
     * Creates a new client
     */
    public Client(ClientDatabaseAPI clientDatabaseAPI, CommonDatabaseAPI commonDatabaseAPI, Runnable endGame) {
        super(commonDatabaseAPI);
        this.clientDatabaseAPI = clientDatabaseAPI;
        this.commonDatabaseAPI = commonDatabaseAPI;
        this.gameStarted = false;
        this.oldSignal = 10;
        this.signal = 0;
        this.endGame = endGame;
    }

    @Override
    public void start() {
        if (!gameStarted) {
            gameStarted = true;

            clientDatabaseAPI.listenToGameStart(start -> {
                if (start.isSuccessful()) {
                    commonDatabaseAPI.fetchPlayers(playerManager.getLobbyDocumentName(), value1 -> {
                        if (value1.isSuccessful()) {
                            addPlayersInPlayerManager(playerManager, value1.getResult());
                            Game.getInstance().addToUpdateList(this);
                            PlayerManager.getInstance().displayPlayers();
                            Game.getInstance().initGame();
                            addListeners();
                        } else
                            Log.d(TAG, "initEnvironment: failed" + value1.getException().getMessage());
                    });
                }
            });
            addListeners();
        }
    }

    private void addListeners() {
        addEnemyListener();
        addItemBoxesListener();
        addPlayersListener();
        addUserItemListener();
        addGameAreaListener();
        addServerAliveSignalListener();
    }

    @Override
    public void update() {
        if (counter <= 0) {
            sendUserPosition();
            sendUsedItems();
            counter = 2 * GameThread.FPS + 1;
        }

        if (counter10Sec <= 0) {
            checkSignalChanged();
            counter10Sec = 10 * GameThread.FPS + 1;
        }

        --counter10Sec;
        --counter;
    }

    private void addServerAliveSignalListener() {
        clientDatabaseAPI.addServerAliveSignalListener((CustomResult<Long> value) -> signal = value.getResult());
    }

    private void checkSignalChanged() {
        if (signal != oldSignal) {
            oldSignal = signal;
        } else {
            updateGeneralScore();
            endGame.run();
            Log.d("Client", "Server does not respond.");
        }
    }

    private void addEnemyListener() {
        clientDatabaseAPI.addCollectionListener(EnemyForFirebase.class, PlayerManager.ENEMY_COLLECTION_NAME, (CustomResult<List<EnemyForFirebase>> value) -> {
            if (value.isSuccessful()) {
                List<EnemyForFirebase> enemyForFirebaseList = new ArrayList<>(value.getResult());
                for (Enemy enemy : EntityConverter.convertEnemyForFirebaseList(enemyForFirebaseList)) {
                    enemyManager.updateEnemies(enemy);
                    Log.d(TAG, "addEnemyListener: " + enemy.getLocation().getLatitude() + enemy.getLocation().getLongitude());
                }
            }
        });
    }

    private void addItemBoxesListener() {
        clientDatabaseAPI.addCollectionListener(ItemBoxForFirebase.class, ItemBoxManager.ITEMBOX_COLLECTION_NAME, (CustomResult<List<ItemBoxForFirebase>> value) -> {
            if (value.isSuccessful()) {
                List<ItemBoxForFirebase> itemBoxForFirebaseList = new ArrayList<>(value.getResult());
                for (ItemBoxForFirebase itemBoxForFirebase : itemBoxForFirebaseList) {
                    String id = itemBoxForFirebase.getId();
                    boolean taken = itemBoxForFirebase.isTaken();
                    GeoPoint location = EntityConverter.geoPointForFirebaseToGeoPoint(itemBoxForFirebase.getLocation());

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
                    Log.d(TAG, "Listen for itemBoxes: " + value.getResult());
                }
            } else {
                Log.w(TAG, "Listen for itemBoxes failed.", value.getException());
            }
        });
    }

    private void addPlayersListener() {
        clientDatabaseAPI.addCollectionListener(PlayerForFirebase.class, PlayerManager.PLAYER_COLLECTION_NAME, (CustomResult<List<PlayerForFirebase>> value) -> {
            if (value.isSuccessful()) {
                for (PlayerForFirebase playerForFirebase : value.getResult()) {
                    Player player = playerManager.getPlayersMap().get(playerForFirebase.getEmail());
                    if (player != null) {
                        player.score.setCurrentGameScore(playerForFirebase.getCurrentGameScore(), player);
                        player.status.setHealthPoints(playerForFirebase.getHealthPoints(), player);
                        player.setLocation(EntityConverter.geoPointForFirebaseToGeoPoint(playerForFirebase.getGeoPointForFirebase()));
                        player.setAoeRadius(playerForFirebase.getAoeRadius());
                        player.status.setPhantom(playerForFirebase.isPhantom(), player);

                        Log.d(TAG, "addPlayersListener: " + player.getEmail());
                    }
                    Log.d(TAG, "Listen for ingameScore: " + playerForFirebase.getUsername() + " " + playerForFirebase.getCurrentGameScore());
                }
            } else {
                Log.w(TAG, "Listen for ingameScore failed.", value.getException());
            }
        });
    }

    private void addGameAreaListener() {
        clientDatabaseAPI.addGameAreaListener(value -> {
            if (value.isSuccessful()) {
                if (area instanceof UnboundedArea) {
                    area = new AreaFactory().getArea(value.getResult());
                    Game.getInstance().addToDisplayList(area);
                    initGameObjects(area);
                }
                area.updateGameArea(new AreaFactory().getArea(value.getResult()));
                Game.getInstance().areaShrinker.showRemainingTime(area.getRemainingTimeString());
            } else {
                Log.w(TAG, "Listen for game area failed.", value.getException());
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
        commonDatabaseAPI.sendUserPosition(EntityConverter.playerToPlayerForFirebase(PlayerManager.getInstance().getCurrentUser()));
    }

    private void sendUsedItems() {
        Map<String, Integer> usedItems = playerManager.getCurrentUser().getInventory().getUsedItems();
        if (!usedItems.isEmpty()) {
            clientDatabaseAPI.sendUsedItems(EntityConverter.convertItems(usedItems));
            playerManager.getCurrentUser().getInventory().clearUsedItems();
        }
    }
}