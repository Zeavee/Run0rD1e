package ch.epfl.sdp.entity;

import ch.epfl.sdp.geometry.GeoPoint;

/**
 * Represents an entity that has AOE radius.
 */
public abstract class AoeRadiusEntity extends Entity {
    private double aoeRadius;

    public AoeRadiusEntity(GeoPoint geoPoint) {
        super(geoPoint);
    }

    public double getAoeRadius() {
        return this.aoeRadius;
    }

    public void setAoeRadius(double aoeRadius) {
        this.aoeRadius = aoeRadius;
    }
}