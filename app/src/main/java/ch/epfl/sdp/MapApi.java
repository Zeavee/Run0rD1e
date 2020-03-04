package ch.epfl.sdp;

import com.google.android.gms.maps.model.LatLng;

public interface MapApi {
    public LatLng getCurrentPosition();

    public void updatePosition();
}
