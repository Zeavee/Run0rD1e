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

public class ClientMockDatabaseAPI implements ClientDatabaseAPI {
    public Map<String, UserForFirebase> userForFirebaseMap = new HashMap<>();
    public Map<String, PlayerForFirebase> playerForFirebaseMap = new HashMap<>();
    public List<EnemyForFirebase> enemyForFirebasesList = new ArrayList<>();
    public List<ItemBoxForFirebase> itemBoxForFirebaseList = new ArrayList<>();
    ItemsForFirebase userItems;

    public void hardCodedInit(Map<String, UserForFirebase> userForFirebaseMap, Map<String, PlayerForFirebase> playerForFirebaseMap, List<EnemyForFirebase> enemyForFirebasesList, List<ItemBoxForFirebase> itemBoxForFirebaseList, ItemsForFirebase userItems){
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
    }

    @Override
    public void setLobbyRef(String lobbyName) {

    }

    @Override
    public void listenToGameStart(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        onValueReadyCallback.finish(new CustomResult<>(null, true, null));
    }

    @Override
    public void addCollectionListener(Object entityType, OnValueReadyCallback<CustomResult<List<Object>>> onValueReadyCallback) {
        List<Object> entityList = new ArrayList<>();
        if (EnemyForFirebase.class.equals(entityType)) {
            entityList.addAll(enemyForFirebasesList);
        } else if (PlayerForFirebase.class.equals(entityType)) {
            entityList.addAll(playerForFirebaseMap.values());
        } else if (ItemBoxForFirebase.class.equals(entityType)) {
            entityList.addAll(itemBoxForFirebaseList);
        }
        onValueReadyCallback.finish(new CustomResult<>(entityList, true, null));
    }

    @Override
    public void addUserItemListener(OnValueReadyCallback<CustomResult<Map<String, Integer>>> onValueReadyCallback) {
        onValueReadyCallback.finish(new CustomResult<>(userItems.getItemsMap(), true, null));
    }

    @Override
    public void addGameAreaListener(OnValueReadyCallback<CustomResult<String>> onValueReadyCallback) {
        
    }

    @Override
    public void sendUserPosition(PlayerForFirebase playerForFirebase) {
        playerForFirebaseMap.put(playerForFirebase.getEmail(), playerForFirebase);
    }

    @Override
    public void sendUsedItems(ItemsForFirebase itemsForFirebase) {
    }
}
