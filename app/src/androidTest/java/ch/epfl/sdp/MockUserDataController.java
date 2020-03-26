package ch.epfl.sdp;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.db.LeaderoardViewModel;

public class MockUserDataController implements UserDataController{
    private List<Player> userData = new ArrayList<>();

    @Override
    public void syncCloudFirebaseToRoom(LeaderoardViewModel leaderoardViewModel) {

    }

    @Override
    public void storeUser(Player player) {
        userData.add(player);
    }
}
