package ch.epfl.sdp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.net.InetAddress;

import static ch.epfl.sdp.ConnectionMode.OFFLINE;

public class InternetConnectionManager {

    private static OfflineAble currentActivity;

    private Thread offlineChecker()
    {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        // check every second to see if connected to the internet
                        Thread.sleep(1000);
                        currentActivity.getActivity().runOnUiThread(() -> {
                            if(!isInternetAvailable())
                                currentActivity.switchMode(OFFLINE);
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        return thread;
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) currentActivity.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private boolean isInternetAvailable() {
        boolean networkConnected = isNetworkConnected();
        try {
            // Fetches the IP address of Google, checking if device is actually connected to the internet not just the network
            InetAddress ip_google = InetAddress.getByName("google.com");
            return (!ip_google.equals(""))&&networkConnected;

        } catch (Exception e) {
            Log.d("Error: ", "Something went wrong connecting to the internet");
            return false;
        }
    }

    // the thread interrupts and handling will now occur on the new (intending) activity's thread stack
    public void setCurrentActivity(OfflineAble intending)
    {
        currentActivity = intending;
    }

    // create singleton of the connection manager
    public static InternetConnectionManager getInstance()
    {
        return new InternetConnectionManager();
    }
}
