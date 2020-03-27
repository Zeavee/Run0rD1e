package ch.epfl.sdp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.FirestoreUserData;
import ch.epfl.sdp.entity.Player;

public class RegisterFormActivity extends AppCompatActivity {
    private final static String REGEX = "^[A-Za-z0-9.]{1,20}@.{1,20}$";
    private static final int MINIMUM_PASSWORD_LENGTH = 8;

    EditText txtUsername, txtEmail, txtPassword, txtPasswordConf;
    Button registerButton;

    public AuthenticationController authenticationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerform);

        txtUsername = findViewById(R.id.username);
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);
        txtPasswordConf = findViewById(R.id.passwordconf);
        registerButton = findViewById(R.id.registerbutton);

        authenticationController = new FirebaseAuthentication(new FirestoreUserData());
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

        Player player = new Player(22, 22, 22, username, email);
        authenticationController.register(RegisterFormActivity.this, player, email, password);
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
