package ch.epfl.sdp.gameOver;

import android.content.Intent;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PlayerManager.getInstance().getCurrentUser().getHealthPoints() > 0) {
            setContentView(R.layout.activity_winner);
            findViewById(R.id.backFromWinnerButton).setOnClickListener(v -> goToMainMenu());
        } else {
            setContentView(R.layout.activity_game_over);
            findViewById(R.id.backFromWinnerButton).setOnClickListener(v -> goToMainMenu());
        }
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
