package ch.epfl.sdp.utils;

import android.app.Application;

import com.google.firebase.FirebaseApp;

/**
 * Custom application class
 */
public class MyApplication extends Application {

    /**
     * Instance of AppContainer that will be used by all the Activities of the app
     */
    public AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        appContainer = new AppContainer();
    }
}
