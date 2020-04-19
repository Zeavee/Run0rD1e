package ch.epfl.sdp.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.epfl.sdp.MainActivity;
import ch.epfl.sdp.R;
import ch.epfl.sdp.database.firebase.CommonFirestoreDatabaseAPI;
import ch.epfl.sdp.database.firebase.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.OnAddUserCallback;
import ch.epfl.sdp.database.firebase.UserForFirebase;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.social.FriendsListActivity;

public class RegisterFormActivity extends AppCompatActivity {
    private static final String REGEX = "^[A-Za-z0-9.]{1,20}@.{1,20}$";
    private static final int MINIMUM_PASSWORD_LENGTH = 8;

    private EditText txtUsername, txtEmail, txtPassword, txtPasswordConf;
    private Button registerButton;

    private AuthenticationAPI authenticationAPI = new FirebaseAuthenticationAPI();
    private CommonDatabaseAPI commonDatabaseAPI = new CommonFirestoreDatabaseAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerform);

        txtUsername = findViewById(R.id.username);
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);
        txtPasswordConf = findViewById(R.id.passwordconf);
        registerButton = findViewById(R.id.registerbutton);
    }

    public void registerBtn_OnClick(View view) {
        final String username = txtUsername.getText().toString();
        final String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String passwordConf = txtPasswordConf.getText().toString().trim();

        switch (checkValidity(email, password, passwordConf)) {
            case 1:
                txtEmail.setError("Email is incorrect");
                return;
            case 2:
                txtPassword.setError("Password is incorrect");
                return;
        }

        register(username, email, password);
    }

    private void register(String username, String email, String password) {
        authenticationAPI.register(email, password, new OnAuthCallback() {
            @Override
            public void finish() {
                UserForFirebase userForFirebase = new UserForFirebase(email, username, 0.0);
                commonDatabaseAPI.addUser(userForFirebase, new OnAddUserCallback() {
                    @Override
                    public void finish() {
                        Log.d("ADD USER", "finish: success");
                        FriendsListActivity.setChatEmailID(email);
                        RegisterFormActivity.this.startActivity(new Intent(RegisterFormActivity.this, MainActivity.class));
                        RegisterFormActivity.this.finish();
                    }

                    @Override
                    public void error(Exception ex) {
                        Toast.makeText(RegisterFormActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void error(Exception ex) {
                Toast.makeText(RegisterFormActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void backBtn_OnClick(View view) {
        startActivity(new Intent(RegisterFormActivity.this, LoginFormActivity.class));
        finish();
    }

    public int checkValidity(String email, String password, String passwordConf) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(email);
        if ((!matcher.matches())) {
            return 1;
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH || (!passwordConf.equals(password))) {
            return 2;
        }
        return 0;
    }
}
