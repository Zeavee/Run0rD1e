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

    /**
     * This is a constructor for the Entity
     * @param geoPoint the location of the entity
     */
    public Entity(GeoPoint geoPoint) {
        location = geoPoint;
    }

    @Override
    public GeoPoint getLocation() {
        return this.location;
    }

    /**
     * This method sets the location of the entity
     * @param location the location we want to set
     */
    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}

