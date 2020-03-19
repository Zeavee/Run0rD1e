package ch.epfl.sdp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import java.net.InetAddress;

import static ch.epfl.sdp.ConnectionMode.OFFLINE;

public class InternetConnectionManager {

    private OfflineAble currentActivity;
    private static final InternetConnectionManager singleton = new InternetConnectionManager();
    private boolean stop = false;
    private Thread timer;
    private AsyncTask timerTask;

    private static class Timer extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
    private InternetConnectionManager()
    {
        timer = new Thread() {
            @Override
            public void run() {
                if (stop) { return; }
                try {
                    while (!isInterrupted()) {
                        // check every 10 second to see if connected to the internet
                        Thread.sleep(10000);
                        singleton.currentActivity.getActivity().runOnUiThread(() -> {
                            if(!isInternetAvailable())
                                singleton.currentActivity.switchMode(OFFLINE);
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

    }
    private static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) singleton.currentActivity.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private static boolean isInternetAvailable() {
        boolean networkConnected = isNetworkConnected();
        try {
            // Fetches the IP address of Google, checking if device is actually connected to the internet not just the network
            InetAddress ip_google = InetAddress.getByName("google.com");
            return (!ip_google.equals(""))&&networkConnected;

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Error: ", "Something went wrong connecting to the net");
            return false;
        }
    }

    // The thread interrupts and handling will now occur on the new (intending) activity's thread stack
    public void setCurrentActivity(OfflineAble intending)
    {
        singleton.currentActivity = intending;
    }

    public void startConnectionMonitor()
    {
        singleton.timer.start();
    }
    public void stopMonitor()
    {
        singleton.stop = true;
    }

    // Return singleton of the connection manager
    public static InternetConnectionManager getInstance()
    {
        return singleton;
    }
}
