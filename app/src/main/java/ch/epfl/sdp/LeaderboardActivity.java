package ch.epfl.sdp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
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

        userDataController.loadUsersForLeaderboard(LeaderboardActivity.this, new SetupLeaderboard(), mUserForFirebases);
    }
}
