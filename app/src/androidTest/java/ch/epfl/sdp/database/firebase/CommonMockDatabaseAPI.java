package ch.epfl.sdp.database.firebase;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.leaderboard.GeneralLeaderboardViewModel;

public class CommonMockDatabaseAPI implements CommonDatabaseAPI {
    private HashMap<String, UserForFirebase> userData;

    public CommonMockDatabaseAPI(HashMap<String, UserForFirebase> userData) {
        this.userData = userData;
    }

    @Override
    public void syncCloudFirebaseToRoom(GeneralLeaderboardViewModel generalLeaderboardViewModel) {

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
        PlayerManager.getInstance().setNumPlayersBeforeJoin(0);
        PlayerManager.getInstance().setIsServer(true);
        onValueReadyCallback.finish(new CustomResult<>(null, true, null));
    }

    @Override
    public void registerToLobby(PlayerForFirebase playerForFirebase, Map<String, Object> data, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        onValueReadyCallback.finish(new CustomResult<>(null, true, null));
    }
}
