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
    public void sendHealthPoints(List<PlayerForFirebase> players, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback) {
        // Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        // Collection Ref
        for (PlayerForFirebase playerForFirebase : players) {
            DocumentReference docRef = firebaseFirestore.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail());
            batch.update(docRef, "healthPoints", playerForFirebase.getHealthPoints());
        }

        batch.commit().addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustumResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustumResult<>(null, false, e)));
    }

    @Override
    public void clearItemBoxes() {

    }

    @Override
    public void sendAoeRadius(List<PlayerForFirebase> players, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback) {
        // Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        // Collection Ref
        for (PlayerForFirebase playerForFirebase : players) {
            DocumentReference docRef = firebaseFirestore.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail());
            batch.update(docRef, "aoeRadius", playerForFirebase.getAoeRadius());
        }

        batch.commit().addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustumResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustumResult<>(null, false, e)));
    }
}
