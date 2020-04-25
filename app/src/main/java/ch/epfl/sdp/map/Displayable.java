package ch.epfl.sdp.map;

import ch.epfl.sdp.geometry.GeoPoint;

public interface Displayable {
    /**
     * Method for getting the location for displaying on the map
     * @return a GeoPoint which is a location
     */
    GeoPoint getLocation();

    void displayOn(MapApi mapApi);

    /**
     * Method to decide if the entity must be displayed only once.
     *
     * @return True if the entity should be displayed only once.
     */
    boolean isOnce();
}
