package ch.epfl.sdp.map.location;

import ch.epfl.sdp.map.location.GeoPoint;

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
