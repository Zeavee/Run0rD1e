package ch.epfl.sdp.database.firebase.api;

import java.util.HashMap;
import java.util.List;

import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.firebase.utils.CustomResult;
import ch.epfl.sdp.database.firebase.utils.OnValueReadyCallback;

public class ClientMockDatabaseAPI extends CommonMockDatabaseAPI implements ClientDatabaseAPI {
    public ClientMockDatabaseAPI(HashMap<String, UserForFirebase> userData, List<Enemy> enemyList) {
        super(userData, enemyList);
    }

    @Override
    public void sendHealthPoints(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {

    }

    @Override
    public void clearItemBoxes() {

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
}
