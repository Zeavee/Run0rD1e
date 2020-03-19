package ch.epfl.sdp;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class MockUserDataController implements UserDataController{
    private List<UserForFirebase> userData = new ArrayList<>();

    @Override
    public void loadUsersForLeaderboard(Activity activity, SetupLeaderboard setupLeaderboard, List<UserForFirebase> mUserForFirebases) {
        mUserForFirebases = userData;
        setupLeaderboard.setupLeaderboardView(activity, mUserForFirebases);
        setupLeaderboard.setupChampions(activity, mUserForFirebases);
    }

    @Override
    public void storeUser(UserForFirebase userForFirebase) {
        userData.add(userForFirebase);
    }
}
