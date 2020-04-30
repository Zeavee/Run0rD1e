package ch.epfl.sdp.map;

import ch.epfl.sdp.geometry.GeoPoint;

public interface LocationFinder {
    /**
     * Method for the current location
     * @return the current location of the phone
     */
    GeoPoint getCurrentLocation();
}
