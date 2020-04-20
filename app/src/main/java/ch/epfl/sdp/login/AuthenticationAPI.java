package ch.epfl.sdp.login;

import com.google.firebase.auth.FirebaseUser;

public interface AuthenticationAPI {
    void signIn(String email, String password, OnAuthCallback callback);

    void register(String email, String password, OnAuthCallback callback);

    String getCurrentUserEmail();

    void signOut();
}
