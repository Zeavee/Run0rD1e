package ch.epfl.sdp.database.firebase.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.ItemBoxManager;

public class ClientMockDatabaseAPI implements ClientDatabaseAPI {
    public Map<String, UserForFirebase> userForFirebaseMap = new HashMap<>();
    public Map<String, PlayerForFirebase> playerForFirebaseMap = new HashMap<>();
    public List<EnemyForFirebase> enemyForFirebasesList = new ArrayList<>();
    public List<ItemBoxForFirebase> itemBoxForFirebaseList = new ArrayList<>();
    ItemsForFirebase userItems;
    String gameArea;

    public void hardCodedInit(Map<String, UserForFirebase> userForFirebaseMap, Map<String, PlayerForFirebase> playerForFirebaseMap, List<EnemyForFirebase> enemyForFirebasesList, List<ItemBoxForFirebase> itemBoxForFirebaseList, ItemsForFirebase userItems, String gameArea) {
        // populate the all Users in firebase
        this.userForFirebaseMap = userForFirebaseMap;

        // populate the Players in lobby
        this.playerForFirebaseMap = playerForFirebaseMap;

        // populate the Enemy
        this.enemyForFirebasesList = enemyForFirebasesList;

        // populate the itemBox
        this.itemBoxForFirebaseList = itemBoxForFirebaseList;

        // populate the usedItem
        this.userItems = userItems;

        // set the game area
        this.gameArea = gameArea;
    }

    @Override
    public void setLobbyRef(String lobbyName) {

    }

    @Override
    public void listenToGameStart(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        onValueReadyCallback.finish(new CustomResult<>(null, true, null));
    }

    @Override
    public <T> void addCollectionListener(Class<T> tClass, String collectionName, OnValueReadyCallback<CustomResult<List<T>>> onValueReadyCallback) {
        List<Object> entityList = new ArrayList<>();
        switch (collectionName) {
            case PlayerManager.ENEMY_COLLECTION_NAME:
                entityList.addAll(enemyForFirebasesList);
                break;
            case PlayerManager.PLAYER_COLLECTION_NAME:
                entityList.addAll(playerForFirebaseMap.values());
                break;
            case ItemBoxManager.ITEMBOX_COLLECTION_NAME:
                entityList.addAll(itemBoxForFirebaseList);
                break;
        }
        onValueReadyCallback.finish(new CustomResult<>((List<T>) entityList, true, null));
    }

    @Override
    public void addUserItemListener(OnValueReadyCallback<CustomResult<Map<String, Integer>>> onValueReadyCallback) {
        onValueReadyCallback.finish(new CustomResult<>(userItems.getItemsMap(), true, null));
    }

    @Override
    public void addGameAreaListener(OnValueReadyCallback<CustomResult<String>> onValueReadyCallback) {
        onValueReadyCallback.finish(new CustomResult<>(gameArea, true, null));
    }

    @Override
    public void sendUsedItems(ItemsForFirebase itemsForFirebase) {
    }

    @Override
    public void cleanListeners() {

    }

    @Override
    public void addServerAliveSignalListener(OnValueReadyCallback<CustomResult<Long>> onValueReadyCallback) {

    }
}
