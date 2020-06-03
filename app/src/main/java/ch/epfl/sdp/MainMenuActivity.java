package ch.epfl.sdp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.epfl.sdp.database.authentication.AuthenticationAPI;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.leaderboard.GeneralLeaderboardActivity;
import ch.epfl.sdp.logic.RuleActivity;
import ch.epfl.sdp.login.LoginFormActivity;
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.social.FriendsListActivity;
import ch.epfl.sdp.utils.JunkCleaner;

/**
 * This is the main menu activity
 * The user can choose here between sending a text to his friends or playing solo/multi-player
 */
public class MainMenuActivity extends AppCompatActivity {
    private AuthenticationAPI authenticationAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authenticationAPI = ((MyApplication) getApplication()).appContainer.authenticationAPI;

        findViewById(R.id.mapButton).setOnClickListener(v -> startWithPlayModeExtra("multi-player"));

        findViewById(R.id.solo).setOnClickListener(v -> startWithPlayModeExtra("single-player"));

        findViewById(R.id.leaderboard).setOnClickListener(view -> startActivity(new Intent(MainMenuActivity.this, GeneralLeaderboardActivity.class)));

        findViewById(R.id.rulesButton).setOnClickListener(v -> startActivity(new Intent(MainMenuActivity.this, RuleActivity.class)));

        findViewById(R.id.logoutBt).setOnClickListener(v -> logout());

        findViewById(R.id.friendsButton).setOnClickListener(v -> startActivity(new Intent(MainMenuActivity.this, FriendsListActivity.class)));
    }

    private void logout() {
        JunkCleaner.clearAll();
        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        appContainer.commonDatabaseAPI.cleanListeners();
        appContainer.serverDatabaseAPI.cleanListeners();
        appContainer.clientDatabaseAPI.cleanListeners();
        authenticationAPI.signOut();
        startActivity(new Intent(MainMenuActivity.this, LoginFormActivity.class));
        finish();
    }

    private void startWithPlayModeExtra(String value){
        Intent intent = new Intent(MainMenuActivity.this, MapsActivity.class);
        intent.putExtra("playMode", value);
        startActivity(intent);
    }
}
    

