package ch.epfl.sdp.map;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.geometry.GeoPoint;

public interface Displayable {
    /**
     * Method for getting the location for displaying on the map
     * @return a GeoPoint which is a location
     */
    public GeoPoint getLocation();

    /**
     * Method to get the type of the object we want to display
     * @return an EntityType which is an enum of types
     */
    public EntityType getEntityType();

    /**
     * Method to decide if the entity must be displayed only once.
     *
     * @return True if the entity should be displayed only once.
     */
    boolean isOnce();
}
