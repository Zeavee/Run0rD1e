package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;

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
}
