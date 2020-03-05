package ch.epfl.sdp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Math.toRadians;

public class GameInfoActivity extends AppCompatActivity {

    Player player = new Player(
            new GeoPoint(toRadians(6.14308), toRadians(46.21023)),
            1.0,
            "admin",
            "admin@epfl.ch");

    TextView username, healthPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        username = findViewById(R.id.username_text);
        username.setText(String.valueOf(player.getUsername()));

        healthPoint = findViewById(R.id.healthpoint_text);
        healthPoint.setText(String.valueOf(player.getHealthPoints()));


        // Update value of health point every 0.1 second
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(() -> updateHealthPoint());
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();

        // just used to show the refresh of health point
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(() -> { if (player.healthPoints > 0) player.healthPoints -= 10; });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread2.start();

    }

    private void updateHealthPoint() {
        healthPoint.setText(String.valueOf(player.getHealthPoints()));
    }
}