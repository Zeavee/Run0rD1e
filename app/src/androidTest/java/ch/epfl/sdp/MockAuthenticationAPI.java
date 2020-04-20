package ch.epfl.sdp;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import ch.epfl.sdp.database.firebase.CommonDatabaseAPI;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.login.AuthenticationAPI;
import ch.epfl.sdp.login.OnAuthCallback;

public class MockAuthenticationAPI implements AuthenticationAPI {
    private HashMap<String, String> registeredUsers;
    private String currentUserEmail;

    @Override
    public void signIn(String email, String password, OnAuthCallback callback) {
        if (!registeredUsers.containsKey(email)) {
            callback.error(new IllegalArgumentException("User not exist!"));
        } else if (registeredUsers.get(email) != password) {
            callback.error(new IllegalArgumentException("Password not correct"));
        } else {
            currentUserEmail = email;
            callback.finish();
        }
    }

    @Override
    public void register(String email, String password, OnAuthCallback callback) {
        if (registeredUsers.containsKey(email)) {
            callback.error(new IllegalArgumentException("User already exist!"));
        } else {
            registeredUsers.put(email, password);
            callback.finish();
        }
    }

    @Override
    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    @Override
    public void signOut() {
        currentUserEmail = null;
    }
}
