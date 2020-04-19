package ch.epfl.sdp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ch.epfl.sdp.MainActivity;
import ch.epfl.sdp.R;
import ch.epfl.sdp.game.CacheableUserInfo;
import ch.epfl.sdp.game.DatabaseHelper;
import ch.epfl.sdp.leaderboard.LeaderboardActivity;
import ch.epfl.sdp.social.FriendsListActivity;

public class LoginFormActivity extends AppCompatActivity {
    public static AuthenticationAPI authenticationAPI = null;
    private EditText lemail, lpassword;
    public static CacheableUserInfo loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);

        lemail = findViewById(R.id.emaillog);
        lpassword = findViewById(R.id.passwordlog);

        // Important to check if dv (dependency visitor) is null, otherwise dependencies could be set by a dependency visitor and thus we wouldn't want to overwrite

        authenticationAPI = (authenticationAPI == null) ? new FirebaseAuthenticationAPI() : authenticationAPI;
        loggedUser = (loggedUser == null) ? new DatabaseHelper(this).getLoggedUser() : loggedUser;

        if (loggedUser != null) {
            signIn(loggedUser.email, loggedUser.password);
        }

        findViewById(R.id.button).setOnClickListener(view -> startActivity(new Intent(LoginFormActivity.this, LeaderboardActivity.class)));

    }

    public void createAccountBtn_OnClick(View view) {
        startActivity(new Intent(LoginFormActivity.this, RegisterFormActivity.class));
        finish();
    }

    public void loginBtn_OnClick(View view) {
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
        authenticationAPI.signIn(email, password, new OnAuthCallback() {
            @Override
            public void finish() {
                FriendsListActivity.setChatEmailID(email);
                LoginFormActivity.this.startActivity(new Intent(LoginFormActivity.this, MainActivity.class));
                LoginFormActivity.this.finish();
                new DatabaseHelper(LoginFormActivity.this).saveLoggedUser(email, password);
            }

            @Override
            public void error(Exception ex) {
                Toast.makeText(LoginFormActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
