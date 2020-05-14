package ch.epfl.sdp.entity;

import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.Positionable;
import ch.epfl.sdp.map.Displayable;

/**
 * Abstract class representing an entity
 */
public abstract class Entity implements Positionable, Displayable {
    /**
     * GeoPoint representing the localisation of the entity
     */
    private GeoPoint location;

    public Entity(GeoPoint geoPoint) {
        location = geoPoint;
    }

    @Override
    public GeoPoint getLocation() {
        return this.location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}

