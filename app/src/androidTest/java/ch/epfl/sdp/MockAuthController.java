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
    public int signIn(String email, String password) {
        signedIn = true;
        Intent myIntent = new Intent(activity, MainActivity.class);
        activity.startActivity(myIntent);
        activity.finish();
        return 0;
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
    public int checkValidity(String email, String password) {
        return 0;
    }


    public boolean isSignedIn(){
        return signedIn;
    }
}
