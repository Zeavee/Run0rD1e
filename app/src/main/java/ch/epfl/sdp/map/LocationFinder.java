package ch.epfl.sdp.map;

import ch.epfl.sdp.geometry.GeoPoint;

/**
 * This interface shows the API of the class that permits to obtain the position of the phone
 */
public interface LocationFinder {
    /**
     * Method for the current location
     *
     * @return the current location of the phone
     */
    GeoPoint getCurrentLocation();
}
