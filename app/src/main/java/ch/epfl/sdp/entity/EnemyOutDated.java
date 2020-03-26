package ch.epfl.sdp.entity;

import ch.epfl.sdp.map.GeoPoint;

public class EnemyOutDated extends MovingEntity {

    GeoPoint location;
    double aoeRadius;

    public EnemyOutDated(double longitude, double latitude, double aoeRadius) {
        location = new GeoPoint(longitude, latitude);
        this.aoeRadius = aoeRadius;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ENEMY;
    }
}
