package ch.epfl.sdp.entity;

import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.Displayable;

/**
 * Abstract class representing a moving entity
 */
public abstract class MovingEntity implements Displayable {
    /**
     * GeoPoint representing the localisation of the entity
     */
    private GeoPoint location;

    public MovingEntity() {
        location = new GeoPoint(0,0);
    }

    @Override
    public GeoPoint getLocation() {
        return this.location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}

