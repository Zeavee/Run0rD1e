package ch.epfl.sdp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);

        lusername = findViewById(R.id.username);
        lemail = findViewById(R.id.emaillog);
        lpassword = findViewById(R.id.passwordlog);
        firebaseAuth = FirebaseAuth.getInstance();
        lLoginButton = findViewById(R.id.loginButton);
    }

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

        //authentificate the user
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginFormActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginFormActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
