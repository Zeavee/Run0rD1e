package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.ItemBoxManager;

public class ClientFirestoreDatabaseAPI implements ClientDatabaseAPI {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private DocumentReference lobbyRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(PlayerManager.getInstance().getLobbyDocumentName());

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


    @Override
    public void addEnemyListener(OnValueReadyCallback<CustomResult<List<EnemyForFirebase>>> onValueReadyCallback) {
        lobbyRef.collection(PlayerManager.ENEMY_COLLECTION_NAME)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
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
    public void addItemBoxesListener(OnValueReadyCallback<CustomResult<List<ItemBoxForFirebase>>> onValueReadyCallback) {
        lobbyRef.collection(ItemBoxManager.ITEMBOX_COLLECTION_NAME)
                .addSnapshotListener((querySnapshot, e) -> {
                    if (e != null) onValueReadyCallback.finish(new CustomResult<>(null, false, e));
                    else {
                        List<ItemBoxForFirebase> itemBoxForFirebaseList = new ArrayList<>();
                        for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                            itemBoxForFirebaseList.add(documentChange.getDocument().toObject(ItemBoxForFirebase.class));
                        }
                        onValueReadyCallback.finish(new CustomResult<>(itemBoxForFirebaseList, true, null));
                    }
                });
    }

    @Override
    public void addUserHealthPointsListener(OnValueReadyCallback<CustomResult<Double>> onValueReadyCallback) {
        lobbyRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(PlayerManager.getInstance().getCurrentUser().getEmail())
                .addSnapshotListener((documentSnapshot, e) -> {
                    if (e != null) onValueReadyCallback.finish(new CustomResult<>(null, false, e));
                    else {
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            onValueReadyCallback.finish(new CustomResult<>((double) documentSnapshot.get("healthPoints"), true, null));
                        }
                    }
                });
    }

    @Override
    public void addUserItemListener(OnValueReadyCallback<CustomResult<Map<String, Integer>>> onValueReadyCallback) {
        lobbyRef.collection(PlayerManager.ITEM_COLLECTION_NAME).document(PlayerManager.getInstance().getCurrentUser().getEmail())
                .addSnapshotListener((documentSnapshot, e) -> {
                    if (e != null) onValueReadyCallback.finish(new CustomResult<>(null, false, e));
                    else {
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            ItemsForFirebase itemsForFirebase = documentSnapshot.toObject(ItemsForFirebase.class);
                            onValueReadyCallback.finish(new CustomResult<>(itemsForFirebase.getItemsMap(), true, null));
                        }
                    }
                });
    }

    @Override
    public void sendUserPosition(PlayerForFirebase playerForFirebase) {
        lobbyRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail()).update("location", playerForFirebase.getLocation());
    }

    @Override
    public void sendUsedItems(ItemsForFirebase itemsForFirebase) {
        lobbyRef.collection(PlayerManager.USED_ITEM_COLLECTION_NAME).document(PlayerManager.getInstance().getCurrentUser().getEmail()).set(itemsForFirebase);
    }


}
