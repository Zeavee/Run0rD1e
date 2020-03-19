package ch.epfl.sdp;

import android.app.Activity;

import java.util.List;

public interface UserDataController {

    void loadUsersForLeaderboard(Activity activity, SetupLeaderboard setupLeaderboard, List<UserForFirebase> mUserForFirebases);

    void storeUser(UserForFirebase userForFirebase);
}
