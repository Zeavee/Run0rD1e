package ch.epfl.sdp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import ch.epfl.sdp.game.CacheableUserInfo;
import ch.epfl.sdp.game.DatabaseHelper;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.FirestoreUserData;
import ch.epfl.sdp.leaderboard.LeaderboardActivity;

public class LoginFormActivity extends AppCompatActivity  {
    public static AuthenticationController authenticationController = null;
    private EditText lemail, lpassword;
    public static CacheableUserInfo loggedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);

        lemail = findViewById(R.id.emaillog);
        lpassword = findViewById(R.id.passwordlog);

        // Important to check if dv (dependency visitor) is null, otherwise dependencies could be set by a dependency visitor and thus we wouldn't want to overwrite

        authenticationController = (authenticationController ==null)?  new FirebaseAuthentication(new FirestoreUserData()) : authenticationController;
        loggedUser = (loggedUser == null) ?  new DatabaseHelper(this).getLoggedUser() : loggedUser;

        if(loggedUser != null)  {
            authenticationController.signIn(LoginFormActivity.this, loggedUser.email, loggedUser.password);
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
        authenticationController.signIn(LoginFormActivity.this, email, password);
    }
}
