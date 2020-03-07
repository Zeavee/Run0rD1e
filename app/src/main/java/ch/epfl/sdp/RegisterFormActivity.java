package ch.epfl.sdp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterFormActivity extends AppCompatActivity {
    EditText txtUsername, txtEmail, txtPassword, txtPasswordConf;
    Button registerButton;
    String userID;
    static AuthenticationController authenticationController;
    UserDataController userDataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerform);

        txtUsername = findViewById(R.id.username);
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);
        txtPasswordConf = findViewById(R.id.passwordconf);
        registerButton = findViewById(R.id.registerbutton);
        userDataController = new FirestoneUserData();

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;

        AuthenticationOutcomeDisplayVisitor authenticationOutcomeDisplayVisitor = new AuthenticationOutcomeDisplayVisitor() {
            @Override
            public void onSuccessfulAuthentication() {
                Toast.makeText(context, "Success!", duration).show();
                Intent myIntent = new Intent(RegisterFormActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }

            @Override
            public void onFailedAuthentication() {
                Toast.makeText(context, "Oups, something went wrong.", duration).show();
            }
        };

        authenticationController = new FirebaseAuthentication(authenticationOutcomeDisplayVisitor,userDataController,this);
    }

    public void registerBtn_OnClick(View view) {
        final String username = txtUsername.getText().toString();
        final String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("email is required");
            return;
        }
        if (TextUtils.isEmpty((password))) {
            txtPassword.setError("password is required");
            return;
        }
        if (password.length() < 8){
            txtPassword.setError("password length has to be greater than 8 Characters");
            return;
        }

       authenticationController.register(email,username,password);
    }

    public void backBtn_OnClick(View view) {
        startActivity(new Intent(this, LoginFormActivity.class));
        finish();
    }
}
