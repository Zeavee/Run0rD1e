package ch.epfl.sdp.login;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
 * This class is designed to use Firebase's email and password feature
 */
public class FirebaseAuthenticationAPI implements AuthenticationAPI {

    private static FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void signIn(String email, String password, OnAuthCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> callback.finish())
                .addOnFailureListener(e -> callback.error(e));
    }

    @Override
    public void register(String email, String password, OnAuthCallback callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> callback.finish())
                .addOnFailureListener(e -> callback.error(e));
    }

    public static FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    @Override
    public void signOut() {
        auth.signOut();
    }
}
