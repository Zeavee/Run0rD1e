package ch.epfl.sdp.database;

import android.app.Activity;

import java.util.List;

import ch.epfl.sdp.leaderboard.SetupLeaderboard;

public interface UserDataController {

    void loadUsersForLeaderboard(Activity activity, SetupLeaderboard setupLeaderboard, List<UserForFirebase> mUserForFirebases);

    void storeUser(UserForFirebase userForFirebase);
//    void storeUser(Player player);
}
