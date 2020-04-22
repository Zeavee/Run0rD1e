package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.utils.CustumResult;
import ch.epfl.sdp.database.firebase.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.ItemBox;

public class ServerFirestoreDatabaseAPI extends CommonFirestoreDatabaseAPI implements ServerDatabaseAPI {
    @Override
    public void sendEnemies(List<EnemyForFirebase> enemies, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback) {
        // Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        // Collection Ref
        for(EnemyForFirebase enemyForFirebase: enemies){
            DocumentReference docRef = firebaseFirestore.collection(PlayerManager.ENEMY_COLLECTION_NAME).document();
            batch.set(docRef, enemyForFirebase);
        }

        batch.commit().addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustumResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustumResult<>(null, false, e)));
    }

    @Override
    public void sendDamage(List<PlayerForFirebase> players, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback) {
        // Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        // Collection Ref
        for(PlayerForFirebase playerForFirebase: players){
            DocumentReference docRef = firebaseFirestore.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail());
            batch.update(docRef, "damage", playerForFirebase.getDamage());
        }

        batch.commit().addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustumResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustumResult<>(null, false, e)));

    }

    @Override
    public void sendItemBox(ItemBox itemBox) {

    }
}
