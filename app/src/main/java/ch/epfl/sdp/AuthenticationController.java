package ch.epfl.sdp;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

public interface AuthenticationController {
    void signIn(Activity activity, String email, String password);

    //    boolean isSignedIn(String email);
    void register(Activity activity, UserForFirebase userForFirebase, String email, String password);

    FirebaseUser getCurrentUser();

    //    void register(Activity activity, Player player, String email, String password);
    void signOut();
}
