package ch.epfl.sdp.entity;

import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.Displayable;

/**
 * Abstract class representing a moving entity
 */
public abstract class MovingEntity implements Displayable {
    /**
     * GeoPoint representing the localisation of the entity
     */
    private GeoPoint location;
    private double aoeRadius;

    public MovingEntity() {
        location = new GeoPoint(0,0);
        aoeRadius = 0;
    }
    public GeoPoint getLocation() {
        return this.location;
    }

    public double getAoeRadius() {
        return this.aoeRadius;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public void setAoeRadius(double aoeRadius) {
        this.aoeRadius = aoeRadius;
    }
}

