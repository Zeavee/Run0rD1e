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

    public MovingEntity(double longitude, double latitude, double aoeRadius) {
        this.location = new GeoPoint(longitude, latitude);
        this.aoeRadius = aoeRadius;
    }

    public abstract void updateLocation();
    //public abstract void updateAoeRadius();

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
