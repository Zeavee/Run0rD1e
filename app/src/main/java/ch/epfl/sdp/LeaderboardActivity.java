package ch.epfl.sdp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    public UserDataController userDataController;
    private List<UserForFirebase> mUserForFirebases;
    private SetupLeaderboard setupLeaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);


        mUserForFirebases = new ArrayList<>();
        setupLeaderboard = new SetupLeaderboard();

        // initialize leaderboard
        UserForFirebase user1 = new UserForFirebase("", "");
        UserForFirebase user2 = new UserForFirebase("", "");
        UserForFirebase user3 = new UserForFirebase("", "");

        List<UserForFirebase> mUsersInit = new ArrayList<>(Arrays.asList(user1, user2, user3));
        setupLeaderboard.setupLeaderboardView(LeaderboardActivity.this, mUsersInit);
        setupLeaderboard.setupChampions(LeaderboardActivity.this, mUsersInit);

        userDataController = new FirestoreUserData();
        userDataController.loadUsersForLeaderboard(LeaderboardActivity.this, setupLeaderboard, mUserForFirebases);
    }
}
