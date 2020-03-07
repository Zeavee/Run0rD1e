package ch.epfl.sdp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFormActivity extends AppCompatActivity {
    private EditText lusername, lemail, lpassword;
    private Button lLoginButton;
    static AuthenticationController authenticationController;
    UserDataController userDataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);

        lusername = findViewById(R.id.username);
        lemail = findViewById(R.id.emaillog);
        lpassword = findViewById(R.id.passwordlog);
        lLoginButton = findViewById(R.id.loginButton);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;

        AuthenticationOutcomeDisplayVisitor authenticationOutcomeDisplayVisitor = new AuthenticationOutcomeDisplayVisitor() {
            @Override
            public void onSuccessfulAuthentication() {
                Toast.makeText(context, "Success!", duration).show();
                Intent myIntent = new Intent(LoginFormActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }

            @Override
            public void onFailedAuthentication() {
                Toast.makeText(context, "Oups, something went wrong.", duration).show();
            }
        };

        authenticationController = new FirebaseAuthentication(authenticationOutcomeDisplayVisitor,userDataController,this);    }

   public void createAccountBtn_OnClick(View view) {
        startActivity(new Intent(this, RegisterFormActivity.class));
        finish();
    }

    public void loginBtn_OnClick(View view) {
        String email = lemail.getText().toString().trim();
        String password = lpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            lemail.setError("email is required");
            return;
        }

        if (TextUtils.isEmpty((password))) {
            lpassword.setError("password is required");
            return;
        }

        if (password.length() < 8) {
            lpassword.setError("password length has to be greater than 8 Characters");
            return;
        }

        authenticationController.signIn(email, password);
    }
}
