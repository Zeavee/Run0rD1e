package ch.epfl.sdp;

import android.os.Bundle;
import android.widget.TextView;
import java.util.*;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Math.toRadians;

public class GameInfoActivity extends AppCompatActivity {

    Player player = new Player(6.149290,
            46.212470,
            50,
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

        // just used to show the refresh of health poin
        Enemy enemy2 = new Enemy(6.149596,46.212437, 50); //enemy2's position is close to player1
        ArrayList<Enemy> enemyArrayList = new ArrayList<Enemy>();
        enemyArrayList.add(enemy2);

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(() -> {
                            if (player.getHealthPoints() > 0)
                                player.updateHealth(enemyArrayList); });
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