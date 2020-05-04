package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;

public class ServerFirestoreDatabaseAPI extends CommonFirestoreDatabaseAPI implements ServerDatabaseAPI {
    @Override
    public void sendEnemies(List<EnemyForFirebase> enemies, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        // Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        // Collection Ref
        for (EnemyForFirebase enemyForFirebase : enemies) {
            DocumentReference docRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(playerManager.getLobbyDocumentName())
                    .collection(PlayerManager.ENEMY_COLLECTION_NAME).document("enemy" + enemyForFirebase.getId());
            batch.set(docRef, enemyForFirebase);
        }

        batch.commit().addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void listenToNumOfPlayers(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        AtomicBoolean flag = new AtomicBoolean(false);
        ListenerRegistration ListenerRegistration = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(playerManager.getLobbyDocumentName())
                .addSnapshotListener((documentSnapshot, e) -> {
                    if ((Long) documentSnapshot.get("count") == PlayerManager.NUMBER_OF_PLAYERS_IN_LOBBY && !flag.get()) {
                        flag.set(true);
                        onValueReadyCallback.finish(new CustomResult<>(null, true, null));
                    }
                });
        if (flag.get()) ListenerRegistration.remove();
    }

    @Override
    public void startGame(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME)
                .document(playerManager.getLobbyDocumentName())
                .update("startGame", true)
                .addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));

    }
}
