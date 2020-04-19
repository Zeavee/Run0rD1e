package ch.epfl.sdp;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.database.firebase.UserDataController;
import ch.epfl.sdp.db.LeaderoardViewModel;
import ch.epfl.sdp.entity.Player;

public class MockUserDataController implements UserDataController {
    private List<Player> userData = new ArrayList<>();

    @Override
    public void syncCloudFirebaseToRoom(LeaderoardViewModel leaderoardViewModel) {

    }

    @Override
    public void storeUser(Player player) {
        userData.add(player);
    }

    @Override
    public void joinLobby(Player player) {

    }

    @Override
    public void fetchPlayers() {

    }
}
