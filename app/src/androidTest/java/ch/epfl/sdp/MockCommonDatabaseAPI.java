package ch.epfl.sdp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.firebase.utils.CustumResult;
import ch.epfl.sdp.database.firebase.utils.OnValueReadyCallback;
import ch.epfl.sdp.leaderboard.LeaderboardViewModel;

public class MockCommonDatabaseAPI implements CommonDatabaseAPI {
    private List<UserForFirebase> userData = new ArrayList<>();

    @Override
    public void syncCloudFirebaseToRoom(LeaderboardViewModel leaderboardViewModel) {

    }

    @Override
    public void addUser(UserForFirebase userForFirebase, OnValueReadyCallback<CustumResult<Void>> OnValueReadyCallback) {

    }

    @Override
    public void fetchUser(String email, OnValueReadyCallback<CustumResult<UserForFirebase>> onValueReadyCallback) {

    }

    @Override
    public void selectLobby(OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback) {

    }

    @Override
    public void registerToLobby(PlayerForFirebase playerForFirebase, Map<String, Object> data, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback) {

    }

    @Override
    public void fetchPlayers(OnValueReadyCallback<CustumResult<List<PlayerForFirebase>>> onValueReadyCallback) {

    }
}
