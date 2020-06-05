package ch.epfl.sdp.gameOver;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.epfl.sdp.MainMenuActivity;
import ch.epfl.sdp.R;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.utils.JunkCleaner;

/**
 * Displays splash-screen-like game over screen that lasts for a few seconds
 */
public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView gameOverText = findViewById(R.id.gameOverText);
        if (PlayerManager.getInstance().getCurrentUser().status.getHealthPoints() > 0) {
            gameOverText.setText(R.string.winnerText);
        }
        findViewById(R.id.backFromGameOver).setOnClickListener(v -> goToMainMenu());
    }

    private void goToMainMenu() {
        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        JunkCleaner.clearAllAndListeners(appContainer);
        Intent i = new Intent(GameOverActivity.this, MainMenuActivity.class);
        startActivity(i);
        finish();
    }
}
