package ch.epfl.sdp;

import android.location.Location;

public interface MapApi {
    public Location getCurrentLocation();

    public void updatePosition();
}
