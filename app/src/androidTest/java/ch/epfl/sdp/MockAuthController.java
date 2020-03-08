package ch.epfl.sdp;

import android.app.Activity;
import android.content.Intent;

public class MockAuthController implements AuthenticationController {
    private boolean signedIn = false;
    Activity activity;

    public MockAuthController(Activity activity){
        this.activity = activity;
    }

    @Override
    public boolean signIn(String email, String password) {
        signedIn = true;
        Intent myIntent = new Intent(activity, MainActivity.class);
        activity.startActivity(myIntent);
        activity.finish();
        return true;
    }

    @Override
    public boolean isSignedIn(String email) {
        return false;
    }

    @Override
    public boolean register(String email, String username, String password) {
        Intent myIntent = new Intent(activity, MainActivity.class);
        activity.startActivity(myIntent);
        activity.finish();
        return true;
    }

    @Override
    public boolean signOut(String email) {
        Intent myIntent = new Intent(activity, LoginFormActivity.class);
        activity.startActivity(myIntent);
        activity.finish();
        signedIn = false;
        return true;
    }

    @Override
    public boolean checkValidity(String email, String password) {
        return true;
    }

    @Override
    public boolean isEmailValid(String email) {
        return false;
    }

    @Override
    public boolean isPasswordValid(String password) {
        return false;
    }

    public boolean isSignedIn(){
        return signedIn;
    }
}
