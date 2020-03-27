package ch.epfl.sdp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import ch.epfl.sdp.database.FirestoreUserData;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.InventoryActivity;
import ch.epfl.sdp.leaderboard.LeaderboardActivity;
import ch.epfl.sdp.logic.GameInfoActivity;
import ch.epfl.sdp.logic.RuleActivity;
import ch.epfl.sdp.login.AuthenticationController;
import ch.epfl.sdp.login.FirebaseAuthentication;
import ch.epfl.sdp.login.LoginFormActivity;
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.social.FriendsListActivity;

public class MainActivity extends AppCompatActivity {
    private static Game game;
    private boolean isFriendsListVisible = false;
    private FragmentTransaction transaction = MainActivity.this.getSupportFragmentManager().beginTransaction();

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

        PlayerManager playerManager = new PlayerManager();

        game = new Game(null);

        startGame();

        authenticationController = new FirebaseAuthentication(new FirestoreUserData());

        // Locate the button in activity_main.xml
        Button healthPointButton = findViewById(R.id.mainGoButton);

        // Capture button clicks
        healthPointButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, GameInfoActivity.class)));

        Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapsActivity.class)));

        // Locate the button in activity_main.xml
        Button leaderboardButton = findViewById(R.id.leaderboard);

        // Capture button clicks
        leaderboardButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LeaderboardActivity.class)));

        Button rulesButton = findViewById(R.id.rulesButton);
        rulesButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RuleActivity.class)));
  
        Button inventory = findViewById(R.id.inventory);
        // Capture button clicks
        inventory.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, InventoryActivity.class);
            startActivity(intent);
        });

        Button logoutButton = findViewById(R.id.logoutBt);
        logoutButton.setOnClickListener(v -> logout());

        Button friendButton = findViewById(R.id.friendsButton);
        friendButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FriendsListActivity.class);
            startActivity(intent);
        });
    }

    public void logout() {
        // Stops the game loop and kills the thread
        MainActivity.killGame();
        authenticationController.signOut();
        startActivity(new Intent(MainActivity.this, LoginFormActivity.class));
        finish();
    }
}
    

