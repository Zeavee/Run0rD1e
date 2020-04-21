package ch.epfl.sdp.entity;

/**
 * Represents a moving entity that has AOE radius.
 */
public class AoeRadiusMovingEntity extends MovingEntity {
    private double aoeRadius;

    public double getAoeRadius() {
        return this.aoeRadius;
    }

    public void setAoeRadius(double aoeRadius) {
        this.aoeRadius = aoeRadius;
    }

    @Override
    public EntityType getEntityType() {
        return null;
    }

    @Override
    public boolean isOnce() {
        return false;
    }
}