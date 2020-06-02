package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;

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
    public <T> void addCollectionListener(Class<T> tClass, String collectionName, OnValueReadyCallback<CustomResult<List<T>>> onValueReadyCallback) {
        lobbyRef.collection(collectionName).addSnapshotListener((querySnapshot, e) -> {
            if (e != null) onValueReadyCallback.finish(new CustomResult<>(null, false, e));
            else {
                List<T> entityList = new ArrayList<>();
                for (DocumentChange documentChange : Objects.requireNonNull(querySnapshot).getDocumentChanges()) {
                    entityList.add(documentChange.getDocument().toObject(tClass));
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
                            onValueReadyCallback.finish(new CustomResult<>(Objects.requireNonNull(itemsForFirebase).getItemsMap(), true, null));
                        }
                    }
                });
    }

    @Override
    public void addGameAreaListener(OnValueReadyCallback<CustomResult<String>> onValueReadyCallback) {
        lobbyRef.addSnapshotListener(((documentSnapshot, e) -> {
            if (e != null) onValueReadyCallback.finish(new CustomResult<>(null, false, e));
            else {
                if (documentSnapshot != null && documentSnapshot.exists() && documentSnapshot.getString(PlayerManager.GAME_AREA_COLLECTION_NAME) != null) {
                    onValueReadyCallback.finish(new CustomResult<>(documentSnapshot.getString(PlayerManager.GAME_AREA_COLLECTION_NAME), true, null));
                }
            }
        }));
    }

    @Override
    public void sendUsedItems(ItemsForFirebase itemsForFirebase) {
        lobbyRef.collection(PlayerManager.USED_ITEM_COLLECTION_NAME).document(PlayerManager.getInstance().getCurrentUser().getEmail()).set(itemsForFirebase);
    }
}
