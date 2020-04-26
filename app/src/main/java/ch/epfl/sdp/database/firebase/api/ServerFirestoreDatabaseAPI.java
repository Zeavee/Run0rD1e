package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.ItemBox;

public class ServerFirestoreDatabaseAPI extends CommonFirestoreDatabaseAPI implements ServerDatabaseAPI {
    @Override
    public void sendEnemies(List<EnemyForFirebase> enemies, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        // Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        // Collection Ref
        for(EnemyForFirebase enemyForFirebase: enemies) {
            DocumentReference docRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(PlayerManager.getLobbyDocumentName())
                    .collection(PlayerManager.ENEMY_COLLECTION_NAME).document("enemy" + enemyForFirebase.getId());
            batch.set(docRef, enemyForFirebase);
        }

        batch.commit().addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void sendDamage(List<PlayerForFirebase> players, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        // Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        // Collection Ref
        for(PlayerForFirebase playerForFirebase: players){
            DocumentReference docRef = firebaseFirestore.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail());
            batch.update(docRef, "damage", playerForFirebase.getDamage());
        }

        batch.commit().addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));

    }

    @Override
    public void sendItemBox(ItemBox itemBox) {

    }
}
