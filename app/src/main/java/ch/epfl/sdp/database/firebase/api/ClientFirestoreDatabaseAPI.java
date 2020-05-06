package ch.epfl.sdp.database.firebase.api;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;

public class ClientFirestoreDatabaseAPI extends CommonFirestoreDatabaseAPI implements ClientDatabaseAPI {
    private DocumentReference lobbyRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(playerManager.getLobbyDocumentName());

    @Override
    public void sendHealthPoints(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        lobbyRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail())
                .update("healthPoints", playerForFirebase.getHealthPoints())
                .addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void sendAoeRadius(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        lobbyRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail())
                .update("aoeRadius", playerForFirebase.getAoeRadius())
                .addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void addEnemyListener(OnValueReadyCallback<CustomResult<List<EnemyForFirebase>>> onValueReadyCallback) {
        lobbyRef.collection(playerManager.ENEMY_COLLECTION_NAME).addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) onValueReadyCallback.finish(new CustomResult<>(null, false, e));
            else {
                List<EnemyForFirebase> enemyForFirebaseList = new ArrayList<>();
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    enemyForFirebaseList.add(documentChange.getDocument().toObject(EnemyForFirebase.class));
                }
                onValueReadyCallback.finish(new CustomResult<>(enemyForFirebaseList, true, null));
            }
        });
    }

    @Override
    public void listenToGameStart(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        AtomicBoolean flag = new AtomicBoolean(false);
        ListenerRegistration ListenerRegistration = lobbyRef.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot != null && (Boolean) documentSnapshot.get("startGame") && !flag.get()) {
                flag.set(true);
                onValueReadyCallback.finish(new CustomResult<>(null, true, null));
            }
        });
        if (flag.get()) ListenerRegistration.remove();
    }
}
