package ch.epfl.sdp.database.authentication;

import com.google.firebase.auth.FirebaseAuth;

import ch.epfl.sdp.utils.CustomResult;
import ch.epfl.sdp.utils.OnValueReadyCallback;

/**
 * This class is designed to use Firebase's authentication feature
 */
public class FirebaseAuthenticationAPI implements AuthenticationAPI {

    private final static FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void signIn(String email, String password, OnValueReadyCallback<CustomResult<Void>> signInCallback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener(e -> signInCallback.finish(new CustomResult<>(null, false, e)))
                .addOnSuccessListener(authResult -> signInCallback.finish(new CustomResult<>(null, true, null)));
    }

    @Override
    public void register(String email, String password, OnValueReadyCallback<CustomResult<Void>> callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> callback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> callback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public String getCurrentUserEmail() {
        return auth.getCurrentUser() == null ? null : auth.getCurrentUser().getEmail();
    }

    @Override
    public void signOut() {
        auth.signOut();
    }
}
