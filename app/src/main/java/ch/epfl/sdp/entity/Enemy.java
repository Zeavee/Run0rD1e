package ch.epfl.sdp.entity;

public class Enemy extends MovingEntity {

    public Enemy(double longitude, double latitude, double aoeRadius) {
        super(longitude, latitude, aoeRadius);
    }

    @Override
    public void updateLocation() {
        //TODO
    }

    public void updateAoeRadius() {
        //TODO
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ENEMY;
    }
}