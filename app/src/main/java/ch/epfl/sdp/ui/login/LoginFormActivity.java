package ch.epfl.sdp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.authentication.AuthenticationAPI;
import ch.epfl.sdp.ui.game.MainMenuActivity;
import ch.epfl.sdp.ui.leader_board.GeneralLeaderBoardActivity;
import ch.epfl.sdp.utils.MyApplication;

/**
 * This is the login activity
 * It is also the starting activity and a user has to log in to play the game
 */
public class LoginFormActivity extends AppCompatActivity {
    private EditText lemail, lpassword;
    private AuthenticationAPI authenticationAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);

        lemail = findViewById(R.id.emaillog);
        lpassword = findViewById(R.id.passwordlog);

        authenticationAPI = ((MyApplication) getApplication()).appContainer.authenticationAPI;

        // If the user has already logged in, go to MainActivity directly
        if (authenticationAPI.getCurrentUserEmail() != null) {
            startActivity(new Intent(LoginFormActivity.this, MainMenuActivity.class));
            finish();
        }

        findViewById(R.id.loginButton).setOnClickListener(this::loginBtn_OnClick);

        findViewById(R.id.createAccountBtn).setOnClickListener(this::createAccountBtn_OnClick);
        // In the offline mode, the user can review the general leaderBoard without login
        findViewById(R.id.offline_button).setOnClickListener(view -> startActivity(new Intent(LoginFormActivity.this, GeneralLeaderBoardActivity.class)));
    }

    private void createAccountBtn_OnClick(View view) {
        startActivity(new Intent(LoginFormActivity.this, RegisterFormActivity.class));
        finish();
    }

    private void loginBtn_OnClick(View view) {
        String email = lemail.getText().toString().trim();
        String password = lpassword.getText().toString().trim();

        if (email.isEmpty()) {
            lemail.setError("Email can't be empty");
            return;
        }
        if (password.isEmpty()) {
            lpassword.setError("Password can't be empty");
            return;
        }
        signIn(email, password);

    }

    private void signIn(String email, String password) {
        authenticationAPI.signIn(email, password, signInRes -> {
            if (!signInRes.isSuccessful()) {
                Log.d("TAG", "signIn: " + signInRes.getException().getMessage());
            } else {
                startActivity(new Intent(LoginFormActivity.this, MainMenuActivity.class));
                finish();
            }
        });
    }
}
