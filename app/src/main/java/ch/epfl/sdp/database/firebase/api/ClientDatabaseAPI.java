package ch.epfl.sdp.database.firebase.api;

import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;

/**
 * The interface with the method related to the firebase firestore
 */
public interface ClientDatabaseAPI {

    void setLobbyRef(String lobbyName);

    /**
     * Listen to game start
     */
    void listenToGameStart(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    void addCollectionListener(Object entityType, OnValueReadyCallback<CustomResult<List<Object>>> onValueReadyCallback);

    void addUserItemListener(OnValueReadyCallback<CustomResult<Map<String, Integer>>> onValueReadyCallback);

    void addGameAreaListener(OnValueReadyCallback<CustomResult<String>> onValueReadyCallback);

    //void sendUserPosition(PlayerForFirebase playerForFirebase);

    void sendUsedItems(ItemsForFirebase itemsForFirebase);
}
