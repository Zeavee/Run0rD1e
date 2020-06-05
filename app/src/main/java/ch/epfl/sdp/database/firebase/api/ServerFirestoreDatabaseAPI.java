package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import ch.epfl.sdp.database.firebase.entityForFirebase.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.UserForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.item.ItemBoxManager;

public class ServerFirestoreDatabaseAPI implements ServerDatabaseAPI {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private DocumentReference lobbyRef;
    private List<ListenerRegistration> listeners = new ArrayList<>();

    @Override
    public void setLobbyRef(String lobbyName) {
        lobbyRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(lobbyName);
    }

    @Override
    public void listenToNumOfPlayers(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        AtomicBoolean flag = new AtomicBoolean(false);
        ListenerRegistration listenerRegistration = lobbyRef.addSnapshotListener((documentSnapshot, e) -> {
            if ((Long) documentSnapshot.get("players") == PlayerManager.NUMBER_OF_PLAYERS_IN_LOBBY && !flag.get()) {
                flag.set(true);
                onValueReadyCallback.finish(new CustomResult<>(null, true, null));
            }
        });
        if (flag.get()) {
            listenerRegistration.remove();
            listeners.remove(listenerRegistration);
        }
        listeners.add(listenerRegistration);
    }

    @Override
    public void startGame(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        lobbyRef.update("startGame", true)
                .addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void fetchGeneralScoreForPlayers(List<String> playerEmailList, OnValueReadyCallback<CustomResult<List<UserForFirebase>>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.USER_COLLECTION_NAME).whereIn("email", playerEmailList).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<UserForFirebase> userForFirebaseList = new ArrayList<>();
                        for (DocumentSnapshot dc : task.getResult().getDocuments()) {
                            userForFirebaseList.add(dc.toObject(UserForFirebase.class));
                        }
                        onValueReadyCallback.finish(new CustomResult<>(userForFirebaseList, true, null));
                    } else {
                        onValueReadyCallback.finish(new CustomResult<>(null, false, task.getException()));
                    }
                });
    }

    @Override
    public void sendEnemies(List<EnemyForFirebase> enemies) {
        sendList(enemies, PlayerManager.ENEMY_COLLECTION_NAME, enemyForFirebase -> ("enemy") + enemyForFirebase.getId(), enemyForFirebase -> enemyForFirebase);
    }

    @Override
    public void sendItemBoxes(List<ItemBoxForFirebase> itemBoxForFirebaseList) {
        sendList(itemBoxForFirebaseList, ItemBoxManager.ITEMBOX_COLLECTION_NAME, ItemBoxForFirebase::getId, itemBoxForFirebase -> itemBoxForFirebase);
    }

    @Override
    public void sendPlayersHealth(List<PlayerForFirebase> playerForFirebaseList) {
        sendPlayersProperty(playerForFirebaseList, "healthPoints", PlayerForFirebase::getHealthPoints);
    }

    private void sendPlayersProperty(List<PlayerForFirebase> playerForFirebaseList, String field, Function<PlayerForFirebase, Double> property) {
        WriteBatch batch = firebaseFirestore.batch();

        for (PlayerForFirebase playerForFirebase : playerForFirebaseList) {
            DocumentReference docRef = lobbyRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail());
            batch.update(docRef, field, property.methodFromT(playerForFirebase));
        }

        batch.commit();
    }

    @Override
    public void sendPlayersItems(Map<String, ItemsForFirebase> emailsItemsMap) {
        WriteBatch batch = firebaseFirestore.batch();

        for (Map.Entry<String, ItemsForFirebase> entry : emailsItemsMap.entrySet()) {
            DocumentReference docRef = lobbyRef.collection(PlayerManager.ITEM_COLLECTION_NAME).document(entry.getKey());
            batch.set(docRef, entry.getValue());
        }

        batch.commit();
    }




    @Override
    public void addUsedItemsListener(OnValueReadyCallback<CustomResult<Map<String, ItemsForFirebase>>> onValueReadyCallback) {
        ListenerRegistration listenerRegistration = lobbyRef.collection(PlayerManager.USED_ITEM_COLLECTION_NAME)
                .addSnapshotListener((documentSnapshot, e) -> {
                    if (e != null) {
                        onValueReadyCallback.finish(new CustomResult<>(null, false, e));
                    } else {
                        Map<String, ItemsForFirebase> emailsItemsMap = new HashMap<>();
                        for (DocumentChange dc : documentSnapshot.getDocumentChanges()) {
                            emailsItemsMap.put(dc.getDocument().getId(), dc.getDocument().toObject(ItemsForFirebase.class));
                        }
                        onValueReadyCallback.finish(new CustomResult<>(emailsItemsMap, true, null));
                    }
                });
        listeners.add(listenerRegistration);
    }

    @Override
    public <T> void addCollectionListener(Class<T> tClass, String collectionName, OnValueReadyCallback<CustomResult<List<T>>> onValueReadyCallback) {
        FireStoreDatabaseAPI.addCollectionListener(tClass, collectionName, onValueReadyCallback, listeners, lobbyRef);
    }

    private <T> void sendList(List<T> list, String collection, Function<T, String> converterToString, Function<T, Object> converterToSend) {
        // Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        // Collection Ref
        for (T entity : list) {
            DocumentReference docRef = lobbyRef.collection(collection).document(converterToString.methodFromT(entity));
            batch.set(docRef, converterToSend.methodFromT(entity));
        }

        batch.commit();
    }

    @Override
    public void sendGameArea(Area gameArea) {
        lobbyRef.update(PlayerManager.GAME_AREA_COLLECTION_NAME, gameArea.toString());
    }

    private interface Function<T, R> {
        R methodFromT(T t);
    }

    @Override
    public void cleanListeners() {
        FireStoreDatabaseAPI.cleanListeners(listeners);
    }

    @Override
    public void sendServerAliveSignal(long signal) {
        Map<String, Object> data = new HashMap<>();
        data.put("signal", signal);
        lobbyRef.update(data);
    }

    @Override
    public void updatePlayersCurrentScore(Map<String, Integer> emailsScoreMap) {
        WriteBatch batch = firebaseFirestore.batch();
        for (Map.Entry<String, Integer> entry : emailsScoreMap.entrySet()) {
            DocumentReference docRef = lobbyRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(entry.getKey());
            batch.update(docRef, "currentGameScore", entry.getValue());
        }
        batch.commit();
    }
}