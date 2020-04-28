package ch.epfl.sdp.database.firebase.api;

import java.util.HashMap;
import java.util.List;

import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;

public class ServerMockDatabaseAPI extends CommonMockDatabaseAPI implements ServerDatabaseAPI {
    public ServerMockDatabaseAPI(HashMap<String, UserForFirebase> userData, List<Enemy> enemyList) {
        super(userData, enemyList);
    }

    @Override
    public void sendEnemies(List<EnemyForFirebase> enemies, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {

    }

    @Override
    public void sendDamage(List<PlayerForFirebase> players, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {

    }
}