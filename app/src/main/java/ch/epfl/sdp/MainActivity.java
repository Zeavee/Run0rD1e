package ch.epfl.sdp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.epfl.sdp.database.FirestoreUserData;
import ch.epfl.sdp.game.DatabaseHelper;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.leaderboard.LeaderboardActivity;
import ch.epfl.sdp.logic.RuleActivity;
import ch.epfl.sdp.login.AuthenticationController;
import ch.epfl.sdp.login.FirebaseAuthentication;
import ch.epfl.sdp.login.LoginFormActivity;
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.social.FriendsListActivity;


public class MainActivity extends AppCompatActivity {
    private static Game game;

    // Launches the game loop in another thread, must be destroyed at the end
    public static void startGame() {
        if (game != null) {
            game.initGame();
        }
    }

    public static void killGame() {
        if (game != null) {
            game.destroyGame();
        }
    }

    public AuthenticationController authenticationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game(null);

        startGame();

        authenticationController = new FirebaseAuthentication(new FirestoreUserData());

        findViewById(R.id.mapButton).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapsActivity.class)));

        findViewById(R.id.leaderboard).setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LeaderboardActivity.class)));

        findViewById(R.id.rulesButton).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RuleActivity.class)));

        findViewById(R.id.logoutBt).setOnClickListener(v -> logout());

        findViewById(R.id.friendsButton).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FriendsListActivity.class)));
    }

    public void logout() {
        // Stops the game loop and kills the thread
        MainActivity.killGame();
        authenticationController.signOut();
        new DatabaseHelper(this).deleteAllUsers();
        startActivity(new Intent(MainActivity.this, LoginFormActivity.class));
        finish();
    }
}
    

