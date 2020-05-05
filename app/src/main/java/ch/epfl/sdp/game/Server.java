package ch.epfl.sdp.game;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ch.epfl.sdp.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.EntityConverter;
import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.LocalArea;
import ch.epfl.sdp.geometry.PointConverter;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.item.ItemBoxManager;
import ch.epfl.sdp.item.ItemFactory;
import ch.epfl.sdp.utils.DependencyFactory;

/**
 * Takes care of all actions that a server should perform (generating enemies, updating enemies etc.).
 */
public class Server implements Updatable {
    private static final String TAG = "Database";
    private int counter;
    private ServerDatabaseAPI serverDatabaseAPI;
    private PlayerManager playerManager = PlayerManager.getInstance();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private ItemFactory itemFactory;

    //private EnemyGenerator enemyGenerator;
    //public final int GENERATE_ENEMY_EVERY_MS = 10000;
    //private double damage;
    //private List<ItemBox> itemBoxes;
    //private int generateEnemyEveryMs;

    public Server() {
        serverDatabaseAPI = DependencyFactory.getServerDatabaseAPI();
        itemFactory = new ItemFactory();

        initEnvironment();
    }

 /*   public Server(EnemyManager manager, EnemyGenerator enemyGenerator) {
        this.enemyGenerator = enemyGenerator;
        this.generateEnemyEveryMs = GENERATE_ENEMY_EVERY_MS;
        Game.getInstance().addToUpdateList(this);
    }

    public Server(EnemyManager manager, EnemyGenerator enemyGenerator, int generateEnemyEveryMs) {
        this(manager, enemyGenerator);
        this.generateEnemyEveryMs = generateEnemyEveryMs;
    }*/

    private void initEnvironment() {
        serverDatabaseAPI.listenToNumOfPlayers(res -> {
            if (res.isSuccessful()) {
                fetchAllPlayersAndListen();
                initItemBoxes();
                initEnemies();
            }
        });
    }

    private void fetchAllPlayersAndListen() {
        // Get Players
        CollectionReference playersCollection = firebaseFirestore.collection(playerManager.LOBBY_COLLECTION_NAME)
                .document(playerManager.getLobbyDocumentName())
                .collection(PlayerManager.PLAYER_COLLECTION_NAME);

        playersCollection.get().addOnSuccessListener(playersDoc -> {

            Iterator<DocumentSnapshot> it = playersDoc.getDocuments().iterator();

            while (it.hasNext()) {
                DocumentSnapshot documentSnapshot = it.next();
                PlayerForFirebase playerForFirebase = documentSnapshot.toObject(PlayerForFirebase.class);
                Player player = EntityConverter.playerForFirebaseToPlayer(playerForFirebase);
                if (!PlayerManager.getInstance().getCurrentUser().getEmail().equals(player.getEmail())) {
                    PlayerManager.getInstance().addPlayer(player);
                }
                Log.d(TAG, "(Server) Getting Player: " + documentSnapshot);
            }

        }).addOnSuccessListener(querySnapshot -> {
            addPlayersPositionListener();
            addUsedItemsListener();
        });
    }

    private void initEnemies() {
        // Enemy -------------------------------------------
        // TODO USE random enemy generator to generate enemy
        GeoPoint local = new GeoPoint(6.2419, 46.2201);
        GeoPoint enemyPos = new GeoPoint(6.3419, 46.2301);
        LocalArea localArea = new LocalArea(new RectangleArea(3500, 3500), PointConverter.geoPointToCartesianPoint(local));
        Enemy enemy = new Enemy(0, localArea, new UnboundedArea());
        enemy.setLocation(enemyPos);
        enemy.setAoeRadius(100);
        SinusoidalMovement movement = new SinusoidalMovement(PointConverter.geoPointToCartesianPoint(enemyPos));
        movement.setVelocity(25);
        movement.setAngleStep(0.1);
        movement.setAmplitude(10);
        enemy.setMovement(movement);
        EnemyManager.getInstance().addEnemy(enemy);
        EnemyManager.getInstance().addEnemiesToGame();
        //  -------------------------------------------

        // Send Enemies
        serverDatabaseAPI.sendEnemies(EntityConverter.enemyToEnemyForFirebase(EnemyManager.getInstance().getEnemies()), value -> {
            if (value.isSuccessful()) {
                serverDatabaseAPI.startGame(value1 -> {
                    if (value1.isSuccessful()) {
                        Game.getInstance().addToUpdateList(this);
                        Game.getInstance().initGame();
                    }
                });
            }
        });
    }

    public void initItemBoxes() {
        // ItemBox -------------------------------------------
        Healthpack healthpack = new Healthpack(10);
        ItemBox itemBox = new ItemBox(new GeoPoint(6.14, 46.22));
        itemBox.putItems(healthpack, 2);
        Game.getInstance().addToDisplayList(itemBox);
        Game.getInstance().addToUpdateList(itemBox);

        Healthpack healthpack1 = new Healthpack(10);
        ItemBox itemBox1 = new ItemBox(new GeoPoint(6.1488, 46.2125));
        itemBox1.putItems(healthpack1, 1);
        Game.getInstance().addToDisplayList(itemBox1);
        Game.getInstance().addToUpdateList(itemBox1);

        ItemBoxManager.getInstance().addItemBox(itemBox); // puts in waiting list
        ItemBoxManager.getInstance().addItemBox(itemBox1);
        //  -------------------------------------------

        sendItemBoxes(); // send them
    }

    private void addUsedItemsListener() {
        CollectionReference itemCollection = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME)
                .document(playerManager.getLobbyDocumentName())
                .collection(PlayerManager.USED_ITEM_COLLECTION_NAME);

        itemCollection.addSnapshotListener((querySnapshot, e) -> {
            if (e != null) {
                Log.w(TAG, "Listen for used items failed.", e);
                return;
            }

            for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                String email = dc.getDocument().getId();
                Map<String, Long> usedItems = (Map<String, Long>) dc.getDocument().get("usedItems");

                for (Map.Entry<String, Long> item : usedItems.entrySet()) {
                    for (int i = 0; i < item.getValue().intValue(); ++i) {
                        itemFactory.getItem(item.getKey()).useOn(PlayerManager.getInstance().getPlayersMap().get(email));
                        PlayerManager.getInstance().getPlayersMap().get(email).getInventory().removeItem(item.getKey());
                    }
                }

                Log.d(TAG, "Listen for used items: " + usedItems);
            }
        });

    }

    private void addPlayersPositionListener() {
        CollectionReference playersCollection = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME)
                .document(playerManager.getLobbyDocumentName())
                .collection(PlayerManager.PLAYER_COLLECTION_NAME);

        playersCollection.addSnapshotListener((querySnapshot, e) -> {
            if (e != null) {
                System.err.println("Listen failed: " + e);
                return;
            }

            for (DocumentChange dc : querySnapshot.getDocumentChanges()) {

                String email = dc.getDocument().getId();
                Map<String, Double> location = (Map<String, Double>) dc.getDocument().get("location");

                GeoPoint geoPoint = new GeoPoint(location.get("longitude"), location.get("latitude"));

                Log.d(TAG, "Get changes for " + email + "'s location: " + location);

                PlayerManager.getInstance().getPlayersMap().get(email).setLocation(geoPoint);
                PlayerManager.getInstance().getPlayersMap().get(email).setPosition(PointConverter.geoPointToCartesianPoint(geoPoint));
            }
        });
    }

    private void sendPlayersHealth() {
        if (!PlayerManager.getInstance().getPlayersWaitingHealthPoint().isEmpty()) {
            // Get a new write batch
            WriteBatch batch = firebaseFirestore.batch();

            // Collection Ref
            for (Player player : playerManager.getPlayers()) {
                DocumentReference playerRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME)
                        .document(playerManager.getLobbyDocumentName())
                        .collection(PlayerManager.PLAYER_COLLECTION_NAME)
                        .document(player.getEmail());
                batch.update(playerRef, "healthPoints", player.getHealthPoints());
            }

            batch.commit().addOnCompleteListener(res -> Log.d("Database", "Complete health"))
                    .addOnSuccessListener(res -> Log.d("Database", "Success health"));

            PlayerManager.getInstance().clearPlayerWaitingHealthPoint();
        }
    }

    private void sendPlayersItems() {
        if (!playerManager.getPlayersWaitingItems().isEmpty()) {
            WriteBatch batch = firebaseFirestore.batch();

            CollectionReference itemsRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME)
                    .document(playerManager.getLobbyDocumentName())
                    .collection(PlayerManager.ITEM_COLLECTION_NAME);

            for (Player player : playerManager.getPlayersWaitingItems()) {
                Log.d("Database", "Sending: to " + player + " player.getInventory().getItems()");
                DocumentReference item_playerRef = itemsRef.document(player.getEmail());
                HashMap hashMap = new HashMap();
                hashMap.put("items", player.getInventory().getItems());
                batch.set(item_playerRef, hashMap);
            }

            batch.commit().addOnSuccessListener(aVoid -> {
                Log.d("Database", "Items sent, clearing waiting list");
                PlayerManager.getInstance().clearPlayerWaitingItems();
            });
        }
    }

    private void sendItemBoxes() {
        ItemBoxManager.getInstance().moveTakenItemBoxesToWaitingList();

        if (!ItemBoxManager.getInstance().getWaitingItemBoxes().isEmpty()) {
            WriteBatch batch = firebaseFirestore.batch();

            CollectionReference itemsRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME)
                    .document(playerManager.getLobbyDocumentName())
                    .collection(ItemBoxManager.ITEMBOX_COLLECTION_NAME);

            for (Map.Entry<String, ItemBox> entry : ItemBoxManager.getInstance().getWaitingItemBoxes().entrySet()) {
                Log.d("Database", "Sending: " + entry.getKey() + entry.getValue());
                DocumentReference itemBoxRef = itemsRef.document(entry.getKey());
                HashMap hashMap = new HashMap();
                hashMap.put("taken", entry.getValue().isTaken());
                hashMap.put("location", entry.getValue().getLocation());
                batch.set(itemBoxRef, hashMap);
            }

            batch.commit().addOnSuccessListener(aVoid -> {
                Log.d("Database", "ItemBoxes sent, clearing waiting list");
                ItemBoxManager.getInstance().clearWaitingItemBoxes();
            });
        }
    }

    @Override
    public void update() {
        if (counter <= 0) {
            //EnemyManager.getInstance().update();
            sendPlayersItems();
            sendItemBoxes();
            sendPlayersHealth();
            counter = 2 * GameThread.FPS + 1;
        }

        --counter;
    }
}