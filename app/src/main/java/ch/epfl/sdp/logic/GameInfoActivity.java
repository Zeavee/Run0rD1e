package ch.epfl.sdp.logic;

import android.os.Bundle;
import android.widget.TextView;
import java.util.*;

import androidx.appcompat.app.AppCompatActivity;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.Player;

public class GameInfoActivity extends AppCompatActivity {

    Player player = new Player(6.149290,
            46.212470,
            50,
            "admin",
            "admin@epfl.ch");
    Enemy enemy = new Enemy(6.149596,46.212437, 50); //enemy's position is close to player
    ArrayList<Enemy> enemyArrayList = new ArrayList<>(Arrays.asList(enemy));

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
        Thread thread = createUpdateHealthPointThread(100);
        thread.start();

        // just used to show the refresh of health point
        Thread thread2 = changeHealthPointUsedForDemo(500);
        thread2.start();
    }

    private Thread createUpdateHealthPointThread(long refreshTime) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(refreshTime);
                        runOnUiThread(() -> updateHealthPoint());
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        return thread;
    }

    private Thread changeHealthPointUsedForDemo(long refreshTime) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(refreshTime);
                        runOnUiThread(() -> {
                            if (player.getHealthPoints() > 0)
                                player.updateHealth(enemyArrayList);});
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        return thread;
    }

    private void updateHealthPoint() {
        double roundOff = Math.round(player.getHealthPoints()*100.0)/100.0;
        healthPoint.setText(String.valueOf(roundOff));
    }
}