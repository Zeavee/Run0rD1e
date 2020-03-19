package ch.epfl.sdp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    public UserDataController userDataController;
    private List<UserForFirebase> mUserForFirebases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        userDataController = new FirestoreUserData();
        mUserForFirebases = new ArrayList<>();
        SetupLeaderboard setupLeaderboard = new SetupLeaderboard();

        // initialize leaderboard
        UserForFirebase user1 = new UserForFirebase("", "");
        UserForFirebase user2 = new UserForFirebase("", "");
        UserForFirebase user3 = new UserForFirebase("", "");

        List<UserForFirebase> mUsersInit = new ArrayList<>(Arrays.asList(user1, user2, user3));
        setupLeaderboard.setupLeaderboardView(LeaderboardActivity.this, mUsersInit);
        setupLeaderboard.setupChampions(LeaderboardActivity.this, mUsersInit);

        userDataController.loadUsersForLeaderboard(LeaderboardActivity.this, new SetupLeaderboard(), mUserForFirebases);
    }
}
