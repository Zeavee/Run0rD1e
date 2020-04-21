package ch.epfl.sdp;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.callback.OnAddUserCallback;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.leaderboard.LeaderboardViewModel;
import ch.epfl.sdp.entity.Player;

public class MockCommonDatabaseAPI implements CommonDatabaseAPI {
    private List<UserForFirebase> userData = new ArrayList<>();

    @Override
    public void syncCloudFirebaseToRoom(LeaderboardViewModel leaderboardViewModel) {

    }

    @Override
    public void addUser(UserForFirebase userForFirebase, OnAddUserCallback onAddUserCallback) {
        userData.add(userForFirebase);
    }

    @Override
    public void joinLobby(Player player) {

    }

    @Override
    public void fetchPlayers() {

    }
}
