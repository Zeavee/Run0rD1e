package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;

public class ClientFirestoreDatabaseAPI extends CommonFirestoreDatabaseAPI implements ClientDatabaseAPI {
    @Override
    public void sendHealthPoints(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME)
                .document(playerManager.getLobbyDocumentName())
                .collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail())
                .update("healthPoints", playerForFirebase.getHealthPoints())
                .addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void sendAoeRadius(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME)
                .document(playerManager.getLobbyDocumentName())
                .collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail())
                .update("aoeRadius", playerForFirebase.getAoeRadius())
                .addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void fetchEnemies(OnValueReadyCallback<CustomResult<List<EnemyForFirebase>>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME)
                .document(playerManager.getLobbyDocumentName())
                .collection(playerManager.ENEMY_COLLECTION_NAME)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<EnemyForFirebase> enemyForFirebases = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        enemyForFirebases.add(document.toObject(EnemyForFirebase.class));
                    }
                    onValueReadyCallback.finish(new CustomResult<>(enemyForFirebases, true, null));
                }).addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void listenToGameStart(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        AtomicBoolean flag = new AtomicBoolean(false);
        ListenerRegistration ListenerRegistration = firebaseFirestore.collection(playerManager.LOBBY_COLLECTION_NAME).document(playerManager.getLobbyDocumentName())
                .addSnapshotListener((documentSnapshot, e) -> {
                    if((Boolean) documentSnapshot.get("startGame") && !flag.get()) {
                        flag.set(true);
                        onValueReadyCallback.finish(new CustomResult<>(null, true, null));
                    }
                });
        if (flag.get()) ListenerRegistration.remove();
    }
}
