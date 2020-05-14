package ch.epfl.sdp.database.firebase.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.GeoPointForFirebase;
import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;

public class ClientMockDatabaseAPI implements ClientDatabaseAPI {
    public Map<String, ItemsForFirebase> usedItems = new HashMap<String, ItemsForFirebase>();

    @Override
    public void setLobbyRef(String lobbyName) {

    }

    @Override
    public void listenToGameStart(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        onValueReadyCallback.finish(new CustomResult<>(null, true, null));
    }

    @Override
    public void addEnemyListener(OnValueReadyCallback<CustomResult<List<EnemyForFirebase>>> onValueReadyCallback) {
        List<EnemyForFirebase> enemyForFirebaseList = new ArrayList<>();
        EnemyForFirebase enemyForFirebase = new EnemyForFirebase(0, new GeoPointForFirebase(22, 22));
        enemyForFirebaseList.add(enemyForFirebase);

        onValueReadyCallback.finish(new CustomResult<>(enemyForFirebaseList, true, null));

    }

    @Override
    public void addItemBoxesListener(OnValueReadyCallback<CustomResult<List<ItemBoxForFirebase>>> onValueReadyCallback) {
        List<ItemBoxForFirebase> itemBoxForFirebaseList = new ArrayList<>();
        ItemBoxForFirebase itemBoxForFirebase0 = new ItemBoxForFirebase("itembox0", new GeoPointForFirebase(22,22), false);
        ItemBoxForFirebase itemBoxForFirebase1 = new ItemBoxForFirebase("itembox1", new GeoPointForFirebase(23,23), true);

        itemBoxForFirebaseList.add(itemBoxForFirebase0);
        itemBoxForFirebaseList.add(itemBoxForFirebase1);
        onValueReadyCallback.finish(new CustomResult<>(itemBoxForFirebaseList, true, null));

    }

    @Override
    public void addUserHealthPointsListener(OnValueReadyCallback<CustomResult<Double>> onValueReadyCallback) {
        onValueReadyCallback.finish(new CustomResult<>(20.0, true, null));

    }

    @Override
    public void addUserItemListener(OnValueReadyCallback<CustomResult<Map<String, Integer>>> onValueReadyCallback) {
        Map<String, Integer> itemsMap = new HashMap<>();
        itemsMap.put("Healthpack 10", 2);
        onValueReadyCallback.finish(new CustomResult<>(itemsMap, true, null));
    }

    @Override
    public void sendUserPosition(PlayerForFirebase playerForFirebase) {

    }

    @Override
    public void sendUsedItems(ItemsForFirebase itemsForFirebase) {
        usedItems.put(PlayerManager.getInstance().getCurrentUser().getEmail(), itemsForFirebase);
    }
}
