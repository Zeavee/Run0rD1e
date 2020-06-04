package ch.epfl.sdp.database.firebase.api;

import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.entityForFirebase.ItemsForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;

/**
 * The interface with the method related to the firebase fireStore
 */
public interface ClientDatabaseAPI {
    /**
     * Set the name of the lobby document in Firebase FireStore
     *
     * @param lobbyName The name of the lobby document in Firebase FireStore
     */
    void setLobbyRef(String lobbyName);

    /**
     * Listen to 'gameStart' field in the lobby document in Firebase FireStore
     * Callback when the 'gameStart' field becomes true
     *
     * @param onValueReadyCallback Callback when the gameStart field becomes true
     */
    void listenToGameStart(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    /**
     * Add a listener to collection in Firebase FireStore
     *
     * @param tClass               Possible values EnemyForFirebase.class / ItemBoxForFirebase.class / PlayerForFirebase.class
     * @param collectionName       corresponding collectionName of tClass
     * @param onValueReadyCallback callback when any document in the collection changed
     * @param <T>                  the type of the class modeled by this {@code Class} object.
     */
    <T> void addCollectionListener(Class<T> tClass, String collectionName, OnValueReadyCallback<CustomResult<List<T>>> onValueReadyCallback);

    /**
     * Add a listener to items taken by the current user, callback when current user take some new items
     *
     * @param onValueReadyCallback Callback when the current user take some new items
     */
    void addUserItemListener(OnValueReadyCallback<CustomResult<Map<String, Integer>>> onValueReadyCallback);

    /**
     * Add a listener to the gameArea and callback when the gameArea changed in the Firebase FireStore
     *
     * @param onValueReadyCallback Callback when the gameArea changed in the Firebase FireStore
     */
    void addGameAreaListener(OnValueReadyCallback<CustomResult<String>> onValueReadyCallback);

    /**
     * Send the used items of the current user to the Firebase FireStore
     *
     * @param itemsForFirebase The itemsForFirebase object which contains the used items of the current user
     */
    void sendUsedItems(ItemsForFirebase itemsForFirebase);

    /**
     * This method cleans the listeners of the API
     */
    void cleanListeners();
}
