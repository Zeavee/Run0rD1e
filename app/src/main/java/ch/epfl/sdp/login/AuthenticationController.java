package ch.epfl.sdp.login;

import android.app.Activity;

import ch.epfl.sdp.database.UserForFirebase;

public interface AuthenticationController {
    void signIn(Activity activity, String email, String password);

    //    boolean isSignedIn(String email);
    void register(Activity activity, UserForFirebase userForFirebase, String email, String password);

    //    void register(Activity activity, Player player, String email, String password);
    void signOut();
}
