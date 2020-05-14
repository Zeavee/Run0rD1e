package ch.epfl.sdp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ch.epfl.sdp.database.authentication.AuthenticationAPI;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.leaderboard.LeaderboardActivity;
import ch.epfl.sdp.logic.RuleActivity;
import ch.epfl.sdp.login.LoginFormActivity;
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.social.FriendsListActivity;


public class MainActivity extends AppCompatActivity {
    private AuthenticationAPI authenticationAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authenticationAPI = ((MyApplication) getApplication()).appContainer.authenticationAPI;

        findViewById(R.id.mapButton).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapsActivity.class)));

        findViewById(R.id.leaderboard).setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LeaderboardActivity.class)));

        findViewById(R.id.rulesButton).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RuleActivity.class)));

        findViewById(R.id.logoutBt).setOnClickListener(v -> logout());

        findViewById(R.id.friendsButton).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FriendsListActivity.class)));
    }

    public void logout() {
        authenticationAPI.signOut();
        Game.getInstance().clearGame();
        Game.getInstance().destroyGame();
        startActivity(new Intent(MainActivity.this, LoginFormActivity.class));
        finish();
    }

    public void backBtn_OnClick(View view) {
        startActivity(new Intent(MainActivity.this, LoginFormActivity.class));
        finish();
    }
}
    

