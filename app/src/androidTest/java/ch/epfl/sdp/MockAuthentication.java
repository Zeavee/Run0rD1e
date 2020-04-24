package ch.epfl.sdp;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import java.util.HashMap;

import ch.epfl.sdp.database.UserDataController;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.login.AuthenticationController;

public class MockAuthentication implements AuthenticationController {
    private HashMap<String, String> registeredUsers;
    private UserDataController store;

    public MockAuthentication(UserDataController store)
    {
        this.store = store;
        this.registeredUsers = new HashMap<>();
    }

    @Override
    public void signIn(Activity loginFormActivity, String email, String password) {
        if (!registeredUsers.containsKey(email)) {
            Toast.makeText(loginFormActivity, "User not exist!", Toast.LENGTH_LONG).show();
        } else if (registeredUsers.get(email) != password) {
            Toast.makeText(loginFormActivity, "Password not correct", Toast.LENGTH_LONG).show();
        } else {
            loginFormActivity.startActivity(new Intent(loginFormActivity, MainActivity.class));
            loginFormActivity.finish();
        }
    }


    @Override
    public void register(Activity registerFormActivity, Player player, String email, String password) {
        if (registeredUsers.containsKey(email)) {
            Toast.makeText(registerFormActivity, "User already exist!", Toast.LENGTH_LONG).show();
        } else {
            registeredUsers.put(email, password);
            store.storeUser("Users", new Player(22,22,22,email, password));
            registerFormActivity.startActivity(new Intent(registerFormActivity, MainActivity.class));
            registerFormActivity.finish();
        }
    }

    @Override
    public void signOut() {
    }

//    @Override
//    public boolean isSignedIn(String email) {
//        return signedIn.containsKey(email);
//    }
}
