package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;

public abstract class DatabaseAPI {

    public static <T> void addCollectionListener(Class<T> tClass, String collectionName, OnValueReadyCallback<CustomResult<List<T>>> onValueReadyCallback, List<ListenerRegistration> listeners, DocumentReference lobbyRef) {
        ListenerRegistration listenerRegistration = lobbyRef.collection(collectionName).addSnapshotListener((querySnapshot, e) -> {
            if (e != null) onValueReadyCallback.finish(new CustomResult<>(null, false, e));
            else {
                List<T> entityList = new ArrayList<>();
                for (DocumentChange documentChange : Objects.requireNonNull(querySnapshot).getDocumentChanges()) {
                    entityList.add(documentChange.getDocument().toObject(tClass));
                }
                onValueReadyCallback.finish(new CustomResult<>(entityList, true, null));
            }
        });
        listeners.add(listenerRegistration);
    }
}
