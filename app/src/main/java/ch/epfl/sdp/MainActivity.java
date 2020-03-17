package ch.epfl.sdp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Locate the button in activity_main.xml
        Button healthPointButton = findViewById(R.id.mainGoButton);

        // Capture button clicks
        healthPointButton.setOnClickListener(view -> {
                    Intent intent = new Intent(MainActivity.this, GameInfoActivity.class);
                    startActivity(intent);
        });

        Button inventory = findViewById(R.id.inventory);

        // Capture button clicks
        inventory.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, InventoryActivity.class);
            startActivity(intent);
        });

        Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapsActivity.class)));

        // Locate the button in activity_main.xml
        Button leaderboardButton = findViewById(R.id.leaderboard);

        // Capture button clicks
        leaderboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
            startActivity(intent);
        });

        Button rulesButton = findViewById(R.id.rulesButton);
        rulesButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RuleActivity.class)));
    }

    public void logout(View view) {
        LoginFormActivity.authenticationController.signOut();
        startActivity(new Intent(MainActivity.this, LoginFormActivity.class));
        finish();
    }
}
