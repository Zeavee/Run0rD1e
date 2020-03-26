package ch.epfl.sdp;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import ch.epfl.sdp.db.LeaderoardViewModel;

public class MockUserDataController implements UserDataController{
    private List<Player> userData = new ArrayList<>();
=======
import ch.epfl.sdp.database.UserDataController;
import ch.epfl.sdp.database.UserForFirebase;
import ch.epfl.sdp.leaderboard.SetupLeaderboard;

public class MockUserDataController implements UserDataController {
    private List<UserForFirebase> userData = new ArrayList<>();
>>>>>>> master

    @Override
    public void syncCloudFirebaseToRoom(LeaderoardViewModel leaderoardViewModel) {

    }

    @Override
    public void storeUser(Player player) {
        userData.add(player);
    }
}
