package ch.epfl.sdp.database.firebase.api;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;

import androidx.annotation.NonNull;
import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;

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
    public void listenToNumOfPlayers(OnValueReadyCallback<CustomResult<Boolean>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(PlayerManager.getLobbyDocumentName())
                .addSnapshotListener((documentSnapshot, e) -> {
                    if((Integer) documentSnapshot.get("count") == PlayerManager.NUMBER_OF_PLAYERS_IN_LOBBY) {
                        onValueReadyCallback.finish(new CustomResult<>(true, true, null));
                    }
                });
    }

    @Override
    public void startGame(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME)
                .document(PlayerManager.getLobbyDocumentName())
                .update("startGame", true)
                .addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null,false,e)));

    }
}
