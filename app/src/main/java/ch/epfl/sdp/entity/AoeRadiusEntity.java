package ch.epfl.sdp.entity;

import ch.epfl.sdp.geometry.GeoPoint;

/**
 * Represents an entity that has AOE radius.
 */
public abstract class AoeRadiusEntity extends Entity {
    private double aoeRadius;

    /**
     * A constructor for the AoeRadiusEntity
     *
     * @param geoPoint the location of the entity
     */
    public AoeRadiusEntity(GeoPoint geoPoint) {
        super(geoPoint);
    }

    /**
     * This method gets the radius of the aoe of the entity
     *
     * @return the radius of the aoe of the entity
     */
    public double getAoeRadius() {
        return this.aoeRadius;
    }

    /**
     * This method sets the radius of the aoe of the entity
     *
     * @param aoeRadius the radius of the aoe of the entity we want to set
     */
    public void setAoeRadius(double aoeRadius) {
        this.aoeRadius = aoeRadius;
    }
}