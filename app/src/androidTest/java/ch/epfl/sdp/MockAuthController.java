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
    public boolean register(String email, String username, String password) {
        Intent myIntent = new Intent(activity, MainActivity.class);
        activity.startActivity(myIntent);
        activity.finish();
        return true;
    }

    @Override
    public boolean signOut() {
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

    public boolean isSignedIn(){
        return signedIn;
    }
}
