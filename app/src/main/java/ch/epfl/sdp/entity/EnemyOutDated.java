package ch.epfl.sdp.entity;

import ch.epfl.sdp.geometry.GeoPoint;

@Deprecated
public class EnemyOutDated extends AoeRadiusMovingEntity {

    GeoPoint location;
    double aoeRadius;
    boolean isActive;

    public EnemyOutDated(double longitude, double latitude, double aoeRadius) {
        location = new GeoPoint(longitude, latitude);
        this.aoeRadius = aoeRadius;
        this.isActive = true;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ENEMY;
    }

    @Override
    public boolean isOnce() {
        return false;
    }
}
