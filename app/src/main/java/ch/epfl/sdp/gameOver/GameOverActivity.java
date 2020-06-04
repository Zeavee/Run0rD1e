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
 * @brief displays splash-screen-like game over screen that lasts for a few seconds
 */
public class GameOverActivity extends AppCompatActivity {
    private TextView gameOverText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        gameOverText = findViewById(R.id.gameOverText);
        if (PlayerManager.getInstance().getCurrentUser().getHealthPoints() > 0) {
            gameOverText.setText(R.string.winnerText);
        }
        findViewById(R.id.backFromGameOver).setOnClickListener(v -> goToMainMenu());
    }

    private void goToMainMenu() {
        JunkCleaner.clearAll();
        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        appContainer.commonDatabaseAPI.cleanListeners();
        appContainer.serverDatabaseAPI.cleanListeners();
        appContainer.clientDatabaseAPI.cleanListeners();
        Intent i = new Intent(GameOverActivity.this, MainMenuActivity.class);
        startActivity(i);
        finish();
    }
}
