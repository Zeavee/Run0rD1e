package ch.epfl.sdp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    TextView loginButton;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerform);

        txtUsername = findViewById(R.id.username);
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);
        txtPasswordConf = findViewById(R.id.passwordconf);
        registerButton = findViewById(R.id.registerbutton);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        //check if the User is already log in or not
        if(firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    private void onUserRegisterFailed() {
        Toast.makeText(RegisterFormActivity.this, "User could not be created!", Toast.LENGTH_SHORT).show();
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

        //register user in the firebase
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()) {
                    onUserRegisterFailed();
                    return;
                }

                Toast.makeText(RegisterFormActivity.this, String.format("User %s with e-mail %s created!", username, email), Toast.LENGTH_SHORT).show();
                userID = firebaseAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fireStore.collection("users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("ID", userID);
                user.put("username", username);
                user.put("email", email);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "onSuccess: user Profile is created for" + userID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterFormActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onUserRegisterFailed();
            }
        });
    }

    public void backBtn_OnClick(View view) {
        startActivity(new Intent(this, LoginFormActivity.class));
        finish();
    }
}
