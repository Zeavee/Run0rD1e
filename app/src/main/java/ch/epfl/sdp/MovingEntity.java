package ch.epfl.sdp;

/**
 * Abstract class representing a moving entity
 */
public abstract class MovingEntity {
    /**
     * GeoPoint representing the localisation of the entity
     */
    GeoPoint location;

    double aoeRadius;

    public MovingEntity(GeoPoint location, double aoeRadius) {
        this.location = location;
        this.aoeRadius = aoeRadius;
    }

    public abstract void updateLocation();
    public abstract void updateAoeRadius();

    public GeoPoint getLocation() {
        return this.location;
    }

    public double getAoeRadius() {
        return this.aoeRadius;
    }
}
