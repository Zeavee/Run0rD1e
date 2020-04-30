package ch.epfl.sdp.database.firebase.api;

import java.util.HashMap;
import java.util.List;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.Enemy;

public class ClientMockDatabaseAPI extends CommonMockDatabaseAPI implements ClientDatabaseAPI {
    public ClientMockDatabaseAPI(HashMap<String, UserForFirebase> userData, List<Enemy> enemyList) {
        super(userData, enemyList);
    }

    @Override
    public void sendHealthPoints(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {

    }

    @Override
    public void sendAoeRadius(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {

    }

    @Override
    public void fetchDamage(OnValueReadyCallback<CustomResult<Double>> onValueReadyCallback) {

    }

    @Override
    public void fetchEnemies(OnValueReadyCallback<CustomResult<List<EnemyForFirebase>>> onValueReadyCallback) {

    }

    @Override
    public void listenToGameStart(OnValueReadyCallback<CustomResult<Boolean>> onValueReadyCallback) {

    }

    @Override
    public void updateLocation(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {

    }
}
