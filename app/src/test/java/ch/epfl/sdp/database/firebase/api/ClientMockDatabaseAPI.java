package ch.epfl.sdp.database.firebase.api;

import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;

public class ClientMockDatabaseAPI implements ClientDatabaseAPI {

    @Override
    public void listenToGameStart(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {

    }

    @Override
    public void addEnemyListener(OnValueReadyCallback<CustomResult<List<EnemyForFirebase>>> onValueReadyCallback) {

    }

    @Override
    public void addItemBoxesListener(OnValueReadyCallback<CustomResult<List<ItemBoxForFirebase>>> onValueReadyCallback) {

    }

    @Override
    public void addUserHealthPointsListener(OnValueReadyCallback<CustomResult<Double>> onValueReadyCallback) {

    }

    @Override
    public void addUserItemListener(OnValueReadyCallback<CustomResult<Map<String, Integer>>> onValueReadyCallback) {

    }

    @Override
    public void sendUserPosition(PlayerForFirebase playerForFirebase) {

    }

    @Override
    public void sendUsedItems(ItemsForFirebase itemsForFirebase) {

    }
}
