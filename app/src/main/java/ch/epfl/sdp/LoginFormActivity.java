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

public class LoginformActivity extends AppCompatActivity {
    EditText lusername, lemail,lpassword;
    Button lLoginButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);

        lusername = findViewById(R.id.username);
        lemail = findViewById(R.id.emaillog);
        lpassword = findViewById(R.id.passwordlog);
        firebaseAuth = FirebaseAuth.getInstance();
        lLoginButton = findViewById(R.id.loginButton);

        lLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            Toast.makeText(LoginformActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else
                            Toast.makeText(LoginformActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }
                });
            }
        });
    }

    public void createAccountBtn_OnClick(View view) {
        startActivity(new Intent(this, RegisterformActivity.class));
    }
}
