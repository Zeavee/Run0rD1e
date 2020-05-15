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
    private DocumentReference lobbyRef;

    public void setLobbyRef(String lobbyName) {
        lobbyRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(lobbyName);
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

    @Override
    public void addCollectionListerner(Object entityType, OnValueReadyCallback<CustomResult<List<Object>>> onValueReadyCallback) {
        String collectionName = "";
        if (EnemyForFirebase.class.equals(entityType)) {
            collectionName = PlayerManager.ENEMY_COLLECTION_NAME;
        } else if (PlayerForFirebase.class.equals(entityType)) {
            collectionName = PlayerManager.PLAYER_COLLECTION_NAME;
        } else if (ItemBoxForFirebase.class.equals(entityType)) {
            collectionName = ItemBoxManager.ITEMBOX_COLLECTION_NAME;
        }

        lobbyRef.collection(collectionName).addSnapshotListener((querySnapshot, e) -> {
            if (e != null) onValueReadyCallback.finish(new CustomResult<>(null, false, e));
            else {
                List<Object> entityList = new ArrayList<>();
                for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                    if (EnemyForFirebase.class.equals(entityType)) {
                        entityList.add(documentChange.getDocument().toObject(EnemyForFirebase.class));
                    } else if (PlayerForFirebase.class.equals(entityType)) {
                        entityList.add(documentChange.getDocument().toObject(PlayerForFirebase.class));
                    } else if (ItemBoxForFirebase.class.equals(entityType)) {
                        entityList.add(documentChange.getDocument().toObject(ItemBoxForFirebase.class));
                    }
                }
                onValueReadyCallback.finish(new CustomResult<>(entityList, true, null));
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
        lobbyRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail()).update("geoPointForFirebase", playerForFirebase.getGeoPointForFirebase());
    }

    @Override
    public void sendUsedItems(ItemsForFirebase itemsForFirebase) {
        lobbyRef.collection(PlayerManager.USED_ITEM_COLLECTION_NAME).document(PlayerManager.getInstance().getCurrentUser().getEmail()).set(itemsForFirebase);
    }
}
