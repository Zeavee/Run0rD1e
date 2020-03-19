package ch.epfl.sdp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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

        Button logoutButton = findViewById(R.id.logoutBt);
        logoutButton.setOnClickListener(v -> logout());
    }

    public void logout() {
        LoginFormActivity.authenticationController.signOut();
        startActivity(new Intent(MainActivity.this, LoginFormActivity.class));
        finish();
    }

    public void onGuestModeClicked(View view)
    {
        startActivity(new Intent(MainActivity.this, OfflineMapsActivity.class));
    }

}
