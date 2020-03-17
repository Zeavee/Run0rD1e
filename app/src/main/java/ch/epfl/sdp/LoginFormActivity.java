package ch.epfl.sdp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginFormActivity extends AppCompatActivity {
    private EditText lemail, lpassword;
    static AuthenticationController authenticationController;
    public UserDataController userDataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);

        lemail = findViewById(R.id.emaillog);
        lpassword = findViewById(R.id.passwordlog);

        userDataController = new FirestoreUserData();
        AuthenticationOutcomeDisplayVisitor authenticationOutcomeDisplayVisitor = new DefaultAuthenticationDisplay(LoginFormActivity.this);
        authenticationController = new FirebaseAuthentication(authenticationOutcomeDisplayVisitor, userDataController,this);
    }

   public void createAccountBtn_OnClick(View view) {
        startActivity(new Intent(this, RegisterFormActivity.class));
        finish();
    }

    public void loginBtn_OnClick(View view) {
        String email = lemail.getText().toString().trim();
        String password = lpassword.getText().toString().trim();
        switch (authenticationController.signIn(email, password))
        {
            case 1: lemail.setError("Email is incorrect"); break;
            case 2: lpassword.setError("Password is incorrect"); break;
        }
    }
}
