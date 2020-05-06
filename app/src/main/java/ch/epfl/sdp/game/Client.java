package ch.epfl.sdp.game;

import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.api.ClientDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.EntityConverter;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.item.ItemBoxManager;
import ch.epfl.sdp.utils.DependencyFactory;

/**
 * This class updates the game from the client point of view. It fetches the data from firebase and
 * the data is updated by the server.
 */
public class Client implements Updatable {
    private static final String TAG = "Database";
    private int counter;
    private ClientDatabaseAPI clientDatabaseAPI = DependencyFactory.getClientDatabaseAPI();
    private PlayerManager playerManager = PlayerManager.getInstance();
    private EnemyManager enemyManager = EnemyManager.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference lobbyRef = firebaseFirestore
            .collection(playerManager.LOBBY_COLLECTION_NAME)
            .document(playerManager.getLobbyDocumentName());

    /**
     * Creates a new client
     */
    public Client() {

        // TODO Add Listener for Enemies and Players
        addUserHealthPointsListener();
        addUserItemListener();
        addItemBoxesListener();
        clientDatabaseAPI.addEnemyListener(value -> {
            if(value.isSuccessful()) {
                for(Enemy enemy: EntityConverter.convertEnemyForFirebaseList(value.getResult())) {
                    enemyManager.updateEnemies(enemy);
                }
            }
        });
        init();

    }

    private void init() {
        clientDatabaseAPI.listenToGameStart(start -> {
            if (start.isSuccessful()) {
                Game.getInstance().addToUpdateList(this);
                Game.getInstance().initGame();
            }
        });
    }

    private void addUserHealthPointsListener() {
        // Listen for healthpoints
        lobbyRef.collection(playerManager.PLAYER_COLLECTION_NAME)
                .document(playerManager.getCurrentUser().getEmail())
                .addSnapshotListener((documentSnapshot, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen for healtpoints failed.", e);
                        return;
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        double healthPoints = (double) documentSnapshot.get("healthPoints");
                        playerManager.getCurrentUser().setHealthPoints(healthPoints);
                        Log.d(TAG, "Listen for healtpoints: " + healthPoints);
                    } else {
                        Log.d(TAG, "Listen for healthpoints: null");
                    }
                });
    }

    private void addUserItemListener() {
        // Listen for Items
        lobbyRef.collection(PlayerManager.ITEM_COLLECTION_NAME)
                .document(playerManager.getCurrentUser().getEmail())
                .addSnapshotListener((documentSnapshot, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen for items failed.", e);
                        return;
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Map<String, Long> uncasteditems = (Map<String, Long>) documentSnapshot.get("items");
                        Map<String, Integer> items = new HashMap<>();

                        for (Map.Entry<String, Long> entry : uncasteditems.entrySet()) {
                            items.put(entry.getKey(), entry.getValue().intValue());
                        }

                        PlayerManager.getInstance().getCurrentUser().getInventory().setItems(items);

                        Log.d(TAG, "Listen for items: " + documentSnapshot.getData());
                    } else {
                        Log.d(TAG, "Listen for items: null");
                    }
                });
    }

    private void addItemBoxesListener() {
        // Listen for Items
        lobbyRef.collection(ItemBoxManager.ITEMBOX_COLLECTION_NAME)
                .addSnapshotListener((querySnapshot, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen for itemBoxes failed.", e);
                        return;
                    }

                    for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                        String id = dc.getDocument().getId();
                        boolean taken = (boolean) dc.getDocument().get("taken");
                        HashMap<String, Double> hashMap = (HashMap<String, Double>) dc.getDocument().get("location");
                        GeoPoint location = new GeoPoint(hashMap.get("longitude"), hashMap.get("latitude"));

                        if (ItemBoxManager.getInstance().getItemBoxes().containsKey(id)) {
                            if (taken) {
                                ItemBox itemBox = ItemBoxManager.getInstance().getItemBoxes().get(id);
                                Game.getInstance().removeFromDisplayList(itemBox);
                                ItemBoxManager.getInstance().getItemBoxes().remove(id);
                            }
                        } else {
                            if (!taken) {
                                ItemBox itemBox = new ItemBox(location);
                                Game.getInstance().addToDisplayList(itemBox);
                                ItemBoxManager.getInstance().addItemBoxWithId(itemBox, id);
                            }
                        }

                        Log.d(TAG, "Listen for itemboxes: " + querySnapshot.getDocumentChanges());
                    }
                });
    }

    private void sendUserPosition() {
        clientDatabaseAPI.updateLocation(EntityConverter.playerToPlayerForFirebase(PlayerManager.getInstance().getCurrentUser()), res -> {
        });
    }

    public void sendUsedItems() {
//        Map<String, Integer> usedItems = PlayerManager.getInstance().getCurrentUser().getInventory().getUsedItems();

        if (!PlayerManager.getInstance().getCurrentUser().getInventory().getUsedItems().isEmpty()) {
            HashMap hashMap = new HashMap();
            hashMap.put("usedItems", PlayerManager.getInstance().getCurrentUser().getInventory().getUsedItems());
            hashMap.put("timeStamp", new Date(System.currentTimeMillis()));

            firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME)
                    .document(playerManager.getLobbyDocumentName())
                    .collection(PlayerManager.USED_ITEM_COLLECTION_NAME).document(PlayerManager.getInstance().getCurrentUser().getEmail())
                    .set(hashMap)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "sendUsedItems: Succeed");
                        PlayerManager.getInstance().getCurrentUser().getInventory().clearUsedItems();
                    })
                    .addOnFailureListener(e -> Log.d(TAG, "onFailure: failed"));
        }
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
}
