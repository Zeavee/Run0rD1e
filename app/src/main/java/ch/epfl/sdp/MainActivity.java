package ch.epfl.sdp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ch.epfl.sdp.database.authentication.AuthenticationAPI;
import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.room.LeaderboardEntity;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.leaderboard.GeneralLeaderboardActivity;
import ch.epfl.sdp.leaderboard.GeneralLeaderboardViewModel;
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

        findViewById(R.id.mapButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra("playMode", "multi-player");
            startActivity(intent);
        });

        findViewById(R.id.solo).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra("playMode", "single-player");
            startActivity(intent);
        });

        findViewById(R.id.leaderboard).setOnClickListener(view -> startActivity(new Intent(MainActivity.this, GeneralLeaderboardActivity.class)));

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
        logout();
    }
}
    

