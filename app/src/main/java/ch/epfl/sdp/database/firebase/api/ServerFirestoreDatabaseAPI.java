package ch.epfl.sdp.database.firebase.api;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.ItemBoxManager;

public class ServerFirestoreDatabaseAPI implements ServerDatabaseAPI {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private DocumentReference lobbyRef;

    public void setLobbyRef(String lobbyName) {
        lobbyRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(lobbyName);
    }

    @Override
    public void listenToNumOfPlayers(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        AtomicBoolean flag = new AtomicBoolean(false);
        ListenerRegistration ListenerRegistration = lobbyRef.addSnapshotListener((documentSnapshot, e) -> {
            if ((Long) documentSnapshot.get("count") == PlayerManager.NUMBER_OF_PLAYERS_IN_LOBBY && !flag.get()) {
                flag.set(true);
                onValueReadyCallback.finish(new CustomResult<>(null, true, null));
            }
        });
        if (flag.get()) ListenerRegistration.remove();
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
        // Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        // Collection Ref
        for (EnemyForFirebase enemyForFirebase : enemies) {
            DocumentReference docRef = lobbyRef.collection(PlayerManager.ENEMY_COLLECTION_NAME).document("enemy" + enemyForFirebase.getId());
            batch.set(docRef, enemyForFirebase);
        }

        batch.commit();
    }

    @Override
    public void sendItemBoxes(List<ItemBoxForFirebase> itemBoxForFirebaseList) {
        WriteBatch batch = firebaseFirestore.batch();

        for (ItemBoxForFirebase itemBoxForFirebase : itemBoxForFirebaseList) {
            DocumentReference docRef = lobbyRef.collection(ItemBoxManager.ITEMBOX_COLLECTION_NAME).document(itemBoxForFirebase.getId());
            batch.set(docRef, itemBoxForFirebase);
        }

        batch.commit();
    }

    @Override
    public void sendPlayersHealth(List<PlayerForFirebase> playerForFirebaseList) {
        WriteBatch batch = firebaseFirestore.batch();

        for (PlayerForFirebase playerForFirebase : playerForFirebaseList) {
            DocumentReference docRef = lobbyRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail());
            batch.update(docRef, "healthPoints", playerForFirebase.getHealthPoints());
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
    public void updatePlayersScore(String scoreType, Map<String, Integer> emailsScoreMap) {
        WriteBatch batch = firebaseFirestore.batch();
        for (Map.Entry<String, Integer> entry : emailsScoreMap.entrySet()) {
            if (scoreType.equals("currentGameScore")) {
                DocumentReference docRef = lobbyRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(entry.getKey());
                batch.update(docRef, "currentGameScore", entry.getValue());
            } else if (scoreType.equals("generalScore")) {
                DocumentReference docRef = firebaseFirestore.collection(PlayerManager.USER_COLLECTION_NAME).document(entry.getKey());
                batch.update(docRef, "generalScore", entry.getValue());
            }
        }
        batch.commit();
    }


    @Override
    public void addUsedItemsListener(OnValueReadyCallback<CustomResult<Map<String, ItemsForFirebase>>> onValueReadyCallback) {
        lobbyRef.collection(PlayerManager.USED_ITEM_COLLECTION_NAME)
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
    }

    @Override
    public void addPlayersPositionListener(OnValueReadyCallback<CustomResult<List<PlayerForFirebase>>> onValueReadyCallback) {
        lobbyRef.collection(PlayerManager.PLAYER_COLLECTION_NAME)
                .addSnapshotListener((querySnapshot, e) -> {
                    if (e != null) {
                        onValueReadyCallback.finish(new CustomResult<>(null, false, e));
                    } else {
                        List<PlayerForFirebase> playerForFirebaseList = new ArrayList<>();
                        for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                            playerForFirebaseList.add(dc.getDocument().toObject(PlayerForFirebase.class));
                        }
                        onValueReadyCallback.finish(new CustomResult<>(playerForFirebaseList, true, null));
                    }
                });
    }
}
