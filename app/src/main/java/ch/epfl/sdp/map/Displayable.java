package ch.epfl.sdp.map;

import ch.epfl.sdp.entity.Entity;
import ch.epfl.sdp.geometry.GeoPoint;

public interface Displayable extends Entity {
    /**
     * Method for getting the location for displaying on the map
     * @return a GeoPoint which is a location
     */
    GeoPoint getLocation();

    /**
     * Method to decide if the entity must be displayed only once.
     *
     * @return True if the entity should be displayed only once.
     */
    boolean isOnce();
}
