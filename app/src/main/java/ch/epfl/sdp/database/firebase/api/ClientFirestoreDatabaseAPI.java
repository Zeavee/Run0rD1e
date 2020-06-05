package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import ch.epfl.sdp.database.firebase.entityForFirebase.ItemsForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;

public class ClientFirestoreDatabaseAPI implements ClientDatabaseAPI {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private DocumentReference lobbyRef;
    private final List<ListenerRegistration> listeners = new ArrayList<>();

    public void setLobbyRef(String lobbyName) {
        lobbyRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(lobbyName);
    }

    @Override
    public void listenToGameStart(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        AtomicBoolean flag = new AtomicBoolean(false);
        ListenerRegistration listenerRegistration = lobbyRef.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot != null && (Boolean) documentSnapshot.get("startGame") && !flag.get()) {
                flag.set(true);
                onValueReadyCallback.finish(new CustomResult<>(null, true, null));
            }
        });
        if (flag.get()) {
            listenerRegistration.remove();
            listeners.remove(listenerRegistration);
        }
        listeners.add(listenerRegistration);
    }

    @Override
    public <T> void addCollectionListener(Class<T> tClass, String collectionName, OnValueReadyCallback<CustomResult<List<T>>> onValueReadyCallback) {
        FireStoreDatabaseAPI.addCollectionListener(tClass, collectionName, onValueReadyCallback, listeners, lobbyRef);
    }

    @Override
    public void addUserItemListener(OnValueReadyCallback<CustomResult<Map<String, Integer>>> onValueReadyCallback) {
        ListenerRegistration listenerRegistration = lobbyRef.collection(PlayerManager.ITEM_COLLECTION_NAME).document(PlayerManager.getInstance().getCurrentUser().getEmail())
                .addSnapshotListener((documentSnapshot, e) -> {
                    if (e != null) onValueReadyCallback.finish(new CustomResult<>(null, false, e));
                    else {
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            ItemsForFirebase itemsForFirebase = documentSnapshot.toObject(ItemsForFirebase.class);
                            onValueReadyCallback.finish(new CustomResult<>(Objects.requireNonNull(itemsForFirebase).getItemsMap(), true, null));
                        }
                    }
                });
        listeners.add(listenerRegistration);
    }

    @Override
    public void addGameAreaListener(OnValueReadyCallback<CustomResult<String>> onValueReadyCallback) {
        ListenerRegistration listenerRegistration = lobbyRef.addSnapshotListener(((documentSnapshot, e) -> {
            if (e != null) onValueReadyCallback.finish(new CustomResult<>(null, false, e));
            else {
                if (documentSnapshot != null && documentSnapshot.exists() && documentSnapshot.getString(PlayerManager.GAME_AREA_COLLECTION_NAME) != null) {
                    onValueReadyCallback.finish(new CustomResult<>(documentSnapshot.getString(PlayerManager.GAME_AREA_COLLECTION_NAME), true, null));
                }
            }
        }));
        listeners.add(listenerRegistration);
    }

    @Override
    public void sendUsedItems(ItemsForFirebase itemsForFirebase) {
        lobbyRef.collection(PlayerManager.USED_ITEM_COLLECTION_NAME).document(PlayerManager.getInstance().getCurrentUser().getEmail()).set(itemsForFirebase);
    }

    @Override
    public void cleanListeners() {
        FireStoreDatabaseAPI.cleanListeners(listeners);
    }

    @Override
    public void addServerAliveSignalListener(OnValueReadyCallback<CustomResult<Long>> onValueReadyCallback) {
        ListenerRegistration listenerRegistration = lobbyRef.addSnapshotListener((documentSnapshot, e) -> {
            if (e != null) onValueReadyCallback.finish(new CustomResult<>(null, false, e));
            else {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Long signal = (Long) documentSnapshot.get("signal");
                    onValueReadyCallback.finish(new CustomResult<>(signal, true, null));
                }
            }
        });

        listeners.add(listenerRegistration);
    }

}
