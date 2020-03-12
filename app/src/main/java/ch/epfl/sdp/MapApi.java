package ch.epfl.sdp;

import android.app.Activity;
import android.location.Location;

public interface MapApi {

    public Location getCurrentLocation();

    public void updatePosition(Activity activity);
}
