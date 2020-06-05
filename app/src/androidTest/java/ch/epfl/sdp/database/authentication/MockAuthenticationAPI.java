package ch.epfl.sdp.database.authentication;

import java.util.HashMap;

import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;


public class MockAuthenticationAPI implements AuthenticationAPI {
    private final HashMap<String, String> registeredUsers;
    private String currentUserEmail;

    public MockAuthenticationAPI(HashMap<String, String> registeredUsers, String currentUserEmail) {
        this.registeredUsers = registeredUsers;
        this.currentUserEmail = currentUserEmail;
    }

    @Override
    public void signIn(String email, String password, OnValueReadyCallback<CustomResult<Void>> callback) {
        if (!registeredUsers.containsKey(email)) {
            callback.finish(new CustomResult<>(null, false, new IllegalArgumentException("User not exist!")));
        } else if (!registeredUsers.get(email).equals(password)) {
            callback.finish(new CustomResult<>(null, false, new IllegalArgumentException("Password not correct")));
        } else {
            currentUserEmail = email;
            callback.finish(new CustomResult<>(null, true, null));
        }
    }

    @Override
    public void register(String email, String password, OnValueReadyCallback<CustomResult<Void>> callback) {
        if (registeredUsers.containsKey(email)) {
            callback.finish(new CustomResult<>(null, false, new IllegalArgumentException("User already exist!")));
        } else {
            registeredUsers.put(email, password);
            callback.finish(new CustomResult<>(null, true, null));
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
