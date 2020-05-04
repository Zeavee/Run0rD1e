package ch.epfl.sdp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ch.epfl.sdp.MainActivity;
import ch.epfl.sdp.R;
import ch.epfl.sdp.utils.DependencyFactory;

public class LoginFormActivity extends AppCompatActivity {
    private EditText lemail, lpassword;
    private AuthenticationAPI authenticationAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);

        lemail = findViewById(R.id.emaillog);
        lpassword = findViewById(R.id.passwordlog);

        authenticationAPI = DependencyFactory.getAuthenticationAPI();

        // If the user has already logged in, go to MainActivity directly
        if(authenticationAPI.getCurrentUserEmail() != null) {
            startActivity(new Intent(LoginFormActivity.this, MainActivity.class));
        }
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
                LoginFormActivity.this.startActivity(new Intent(LoginFormActivity.this, MainActivity.class));
                LoginFormActivity.this.finish();
            }

            @Override
            public void error(Exception ex) {
                Toast.makeText(LoginFormActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
