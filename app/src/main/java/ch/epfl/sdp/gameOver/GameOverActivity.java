package ch.epfl.sdp.gameOver;

import androidx.appcompat.app.AppCompatActivity;

import ch.epfl.sdp.MainActivity;
import ch.epfl.sdp.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

/**
 * @brief displays splash-screen-like game over screen that lasts for a few seconds
 */
public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        findViewById(R.id.backFromGameOverButton).setOnClickListener(v -> goToMainMenu());
    }

    private void goToMainMenu() {
        Intent i = new Intent(GameOverActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
