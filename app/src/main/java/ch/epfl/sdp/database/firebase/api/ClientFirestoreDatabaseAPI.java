package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.utils.CustumResult;
import ch.epfl.sdp.database.firebase.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;

public class ClientFirestoreDatabaseAPI extends CommonFirestoreDatabaseAPI implements ClientDatabaseAPI {
    @Override
    public void sendHealthPoints(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail())
                .update("healthPoints", playerForFirebase.getHealthPoints())
                .addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustumResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustumResult<>(null, false, e)));
    }

    @Override
    public void clearItemBoxes() {

    }

    @Override
    public void sendAoeRadius(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail())
                .update("aoeRadius", playerForFirebase.getAoeRadius())
                .addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustumResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustumResult<>(null, false, e)));
    }

    @Override
    public void fetchDamage(OnValueReadyCallback<CustumResult<Double>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME)
                .document(PlayerManager.getLobbyDocumentName())
                .collection(PlayerManager.PLAYER_COLLECTION_NAME)
                .document(PlayerManager.getCurrentUser().getEmail())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    PlayerForFirebase playerForFirebase = documentSnapshot.toObject(PlayerForFirebase.class);
                    onValueReadyCallback.finish(new CustumResult<>(playerForFirebase.getDamage(), true, null));
                }).addOnFailureListener(e -> onValueReadyCallback.finish(new CustumResult<>(null, false, e)));
    }

    @Override
    public void fetchEnemies(OnValueReadyCallback<CustumResult<List<EnemyForFirebase>>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME)
                .document(PlayerManager.getLobbyDocumentName())
                .collection(PlayerManager.ENEMY_COLLECTION_NAME)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<EnemyForFirebase> enemyForFirebases = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        enemyForFirebases.add(document.toObject(EnemyForFirebase.class));
                    }
                    onValueReadyCallback.finish(new CustumResult<>(enemyForFirebases, true, null));
                }).addOnFailureListener(e -> onValueReadyCallback.finish(new CustumResult<>(null, false, e)));
    }
}