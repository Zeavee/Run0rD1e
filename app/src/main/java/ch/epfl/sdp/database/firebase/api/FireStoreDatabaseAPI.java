package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ch.epfl.sdp.utils.CustomResult;
import ch.epfl.sdp.utils.OnValueReadyCallback;

/**
 * This interface is only used to implement methods that are common between all our FireStoreAPI
 */
public interface FireStoreDatabaseAPI {

    /**
     * Add a listener to collection in Firebase FireStore
     *
     * @param tClass               Possible values EnemyForFirebase.class / ItemBoxForFirebase.class / PlayerForFirebase.class
     * @param collectionName       corresponding collectionName of tClass
     * @param onValueReadyCallback callback when any document in the collection changed
     * @param listeners            the list of listeners of the API, so we can keep track of the listeners
     * @param lobbyRef             the document reference we want to add the listener on
     * @param <T>                  the type of the class modeled by this {@code Class} object.
     */
    static <T> void addCollectionListener(Class<T> tClass, String collectionName, OnValueReadyCallback<CustomResult<List<T>>> onValueReadyCallback, List<ListenerRegistration> listeners, DocumentReference lobbyRef) {
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

    /**
     * This method cleans the given list of listeners of the API
     *
     * @param listeners the given list of listeners
     */
    static void cleanListeners(List<ListenerRegistration> listeners) {
        for (ListenerRegistration listener : listeners) {
            listener.remove();
        }
        listeners.clear();
    }
}
