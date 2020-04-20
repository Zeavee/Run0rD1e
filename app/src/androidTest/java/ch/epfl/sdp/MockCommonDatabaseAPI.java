package ch.epfl.sdp;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.database.firebase.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.OnAddUserCallback;
import ch.epfl.sdp.database.firebase.UserForFirebase;
import ch.epfl.sdp.database.room.LeaderoardViewModel;
import ch.epfl.sdp.entity.Player;

public class MockCommonDatabaseAPI implements CommonDatabaseAPI {
    private List<UserForFirebase> userData = new ArrayList<>();

    @Override
    public void syncCloudFirebaseToRoom(LeaderoardViewModel leaderoardViewModel) {

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
