package ch.epfl.sdp.login;

import android.app.Activity;

import ch.epfl.sdp.entity.Player;

public interface AuthenticationController {
    //    boolean isSignedIn(String email);
    String getEmailOfCurrentUser();
    public abstract void signIn(Activity activity, String email, String password);
    public abstract void register(Activity activity, Player player, String email, String password);
    public abstract void signOut();
}
