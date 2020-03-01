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

public class RegisterformActivity extends AppCompatActivity {
    EditText rusername, remail, rpassword, rpasswordConf;
    Button registerButton;
    TextView loginButton;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerform);

        rusername = findViewById(R.id.username);
        remail = findViewById(R.id.email);
        rpassword = findViewById(R.id.password);
        rpasswordConf = findViewById(R.id.passwordconf);
        registerButton = findViewById(R.id.registerbutton);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        //check if the User is already log in or not
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

        }
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = remail.getText().toString().trim();
                String password = rpassword.getText().toString().trim();
                final String username = rusername.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    remail.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty((password))) {
                    rpassword.setError("password is required");
                    return;
                }
                if (password.length() < 8){
                    rpassword.setError("password length has to be greater than 8 Characters");
                    return;
                }

                //register user in the firebase
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterformActivity.this, "user created ", Toast.LENGTH_SHORT).show();
                            userID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fireStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("username",rusername);
                            user.put("email",remail);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: user Profile is created for" + userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else
                            Toast.makeText(RegisterformActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String msg = e.getMessage();
                        int a = 5;
                    }
                });
            }
        });
    }
}
