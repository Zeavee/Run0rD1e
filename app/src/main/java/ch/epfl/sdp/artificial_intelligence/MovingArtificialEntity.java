package ch.epfl.sdp.artificial_intelligence;

import java.util.Random;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.MovingEntity;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

public class MovingArtificialEntity extends MovingEntity implements Movable, Positionable, Updatable {
    private Movement movement;
    private Area bounds;
    private boolean moving;
    private boolean forceMove = false;
    private Random rand = new Random();
    ;

    public MovingArtificialEntity() {
        super();
        movement = new LinearMovement(PointConverter.geoPointToGenPoint(getLocation()));
        bounds = new UnboundedArea();
        moving = true;
    }

    public MovingArtificialEntity(Movement movement, Area bounds, boolean moving) {
        super();
        this.movement = movement;
        this.bounds = bounds;
        this.moving = moving;
    }

    public Area getBounds() {
        return bounds;
    }

    public void setBounds(Area bounds) {
        this.bounds = bounds;
    }

    public void setForceMove(boolean forceMove) {
        this.forceMove = forceMove;
    }

    @Override
    public void move() {
        GenPoint position = movement.nextPosition();
        if (bounds.isInside(position) || forceMove) {
            movement.setPosition(position);
            super.setLocation(PointConverter.genPointToGeoPoint(position, MapsActivity.mapApi.getCurrentLocation()));
        } else {
            switchOnMouvement();
        }
    }

    @Override
    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void switchOnMouvement() {
        movement.setOrientation(rand.nextFloat() * 2 * (float) (Math.PI));
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement){
        this.movement = movement;
    }

    @Override
    public void setLocation(GeoPoint geoPoint) {
        super.setLocation(geoPoint);
        movement.setPosition(PointConverter.geoPointToGenPoint(geoPoint));
    }

    @Override
    public void update() {
        if (moving) {
            move();
        }
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.NONE;
    }

    @Override
    public boolean once() {
        return false;
    }

    @Override
    public GenPoint getPosition() {
        return movement.getPosition();
    }

    public void setPosition(GenPoint position) {
        movement.setPosition(position);
    }
}
