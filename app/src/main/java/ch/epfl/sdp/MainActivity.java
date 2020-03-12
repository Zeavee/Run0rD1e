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

        // Locate the button in activity_main.xml
        Button leaderboardButton = findViewById(R.id.leaderboard);

        // Capture button clicks
        leaderboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
            startActivity(intent);
        });

        Button mapButton = (Button) findViewById(R.id.mapButton);

        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });

        Button rulesButton = (Button) findViewById(R.id.rulesButton);

        rulesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RuleActivity.class));
            }
        });
    }
}
