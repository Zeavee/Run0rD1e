package ch.epfl.sdp.login;

import android.app.Activity;

import ch.epfl.sdp.entity.Player;

public interface AuthenticationAPI {

    //    void signIn(Activity activity, String email, String password);
    void signIn(String email, String password, OnAuthCallback callback);


    void register(Activity activity, Player player, String email, String password);

    void signOut();
}
