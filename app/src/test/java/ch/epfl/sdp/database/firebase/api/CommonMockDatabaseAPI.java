package ch.epfl.sdp.database.firebase.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.leaderboard.LeaderboardViewModel;

public class CommonMockDatabaseAPI implements CommonDatabaseAPI {
    protected HashMap<String, UserForFirebase> userData;
    protected List<Enemy> enemies;

    public CommonMockDatabaseAPI(HashMap<String, UserForFirebase> userData, List<Enemy> enemyList) {
        this.userData = userData;
        this.enemies = enemyList;
    }

    @Override
    public void syncCloudFirebaseToRoom(LeaderboardViewModel leaderboardViewModel) {

    }

    @Override
    public void addUser(UserForFirebase userForFirebase, OnValueReadyCallback<CustomResult<Void>> OnValueReadyCallback) {
        if(userData.containsKey(userForFirebase.getEmail())) {
            OnValueReadyCallback.finish(new CustomResult<>(null, false, new IllegalArgumentException("Already exist!")));
        } else {
            userData.put(userForFirebase.getEmail(), userForFirebase);
            OnValueReadyCallback.finish(new CustomResult<>(null, true, null));
        }
    }

    @Override
    public void fetchUser(String email, OnValueReadyCallback<CustomResult<UserForFirebase>> onValueReadyCallback) {
        if(!userData.containsKey(email)) {
            onValueReadyCallback.finish(new CustomResult<>(null, false, new IllegalArgumentException("Not exists!")));
        } else {
            onValueReadyCallback.finish(new CustomResult<>(userData.get(email), true, null));
        }
    }

    @Override
    public void selectLobby(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {

    }

    @Override
    public void registerToLobby(PlayerForFirebase playerForFirebase, Map<String, Object> data, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {

    }

    @Override
    public void fetchPlayers(OnValueReadyCallback<CustomResult<List<PlayerForFirebase>>> onValueReadyCallback) {

    }
}
