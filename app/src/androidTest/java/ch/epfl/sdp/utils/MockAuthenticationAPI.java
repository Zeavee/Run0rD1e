package ch.epfl.sdp.utils;

import java.util.HashMap;

import ch.epfl.sdp.login.AuthenticationAPI;
import ch.epfl.sdp.login.OnAuthCallback;

public class MockAuthenticationAPI implements AuthenticationAPI {
    private HashMap<String, String> registeredUsers;
    private String currentUserEmail;

    public MockAuthenticationAPI(HashMap<String, String> registeredUsers, String currentUserEmail) {
        this.registeredUsers = registeredUsers;
        this.currentUserEmail = currentUserEmail;
    }

    @Override
    public void signIn(String email, String password, OnAuthCallback callback) {
        if (!registeredUsers.containsKey(email)) {
            callback.error(new IllegalArgumentException("User not exist!"));
        } else if (!registeredUsers.get(email).equals(password)) {
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
