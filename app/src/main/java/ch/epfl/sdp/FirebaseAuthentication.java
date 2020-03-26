package ch.epfl.sdp;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/*
 * This class is designed to use Firebase's email and password feature
 */
public class FirebaseAuthentication implements AuthenticationController {

    /* This regular expression pattern allows for matching emails of
     * the form [alphanumeric characters or dot]@[any characters]
     * where the part before the @ sign can have length ranging from
     *  1 to 20 and the part after can have length ranging from 1 to 20
     * useful for sanitizing input
     */
    private FirebaseAuth auth;
    private UserDataController userDataStore;

    public FirebaseAuthentication(UserDataController store) {
        this.auth = FirebaseAuth.getInstance();
        this.userDataStore = store;
    }

    @Override
    public void signIn(Activity loginFormActivity, String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            loginFormActivity.startActivity(new Intent(loginFormActivity, MainActivity.class));
            loginFormActivity.finish();
        }).addOnFailureListener(e -> Toast.makeText(loginFormActivity, e.getMessage(), Toast.LENGTH_LONG).show());
    }

//    @Override
//    public boolean isSignedIn(String email) {
//        return auth.getCurrentUser() != null;
//    }

    @Override
    public void register(Activity registerFormActivity, Player userForFirebase, String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            userDataStore.storeUser(userForFirebase);
            registerFormActivity.startActivity(new Intent(registerFormActivity, MainActivity.class));
            registerFormActivity.finish();
        }).addOnFailureListener(e -> Toast.makeText(registerFormActivity, e.getMessage(), Toast.LENGTH_LONG).show());
    }

    @Override
    public void signOut() {
        auth.signOut();
    }
}
