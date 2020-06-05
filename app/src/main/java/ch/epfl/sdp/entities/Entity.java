package ch.epfl.sdp.entities;

import ch.epfl.sdp.map.location.GeoPoint;
import ch.epfl.sdp.map.location.Positionable;
import ch.epfl.sdp.map.display.Displayable;

/**
 * Abstract class representing an entity
 */
public abstract class Entity implements Displayable {
    /**
     * GeoPoint representing the localisation of the entity
     */
    private GeoPoint location;

    /**
     * This is a constructor for the Entity
     *
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
     *
     * @param location the location we want to set
     */
    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}