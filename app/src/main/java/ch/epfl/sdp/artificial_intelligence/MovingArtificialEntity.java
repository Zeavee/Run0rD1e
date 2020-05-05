package ch.epfl.sdp.artificial_intelligence;

import java.util.Random;

import ch.epfl.sdp.entity.AoeRadiusMovingEntity;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.CartesianPoint;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.PointConverter;
import ch.epfl.sdp.geometry.Positionable;
import ch.epfl.sdp.geometry.UnboundedArea;

/**
 * Represents an entity of the game that can move automatically by setting a movement. The movement
 * can be limited by setting an area where the entity can reside.
 */
public abstract class MovingArtificialEntity extends AoeRadiusMovingEntity implements Movable, Positionable, Updatable {
    private Movement movement;
    private Area area;
    private boolean moving;
    /**
     * When the forceMove is true the entity is allowed to go move outside the area.
     */
    private boolean forceMove = false;
    private Random rand = new Random();

    /**
     * Creates a default moving artificial entity, without a bounded area and with a linear
     * movement.
     */
    public MovingArtificialEntity() {
        super();
        movement = new LinearMovement(PointConverter.geoPointToCartesianPoint(getLocation()));
        area = new UnboundedArea();
        moving = true;
    }

    /**
     * Creates a default moving artificial entity, by specifying a movement, an area and if it's
     * already moving.
     *
     * @param movement the type of movement the entity use
     * @param area the area the entity can move in
     * @param moving a boolean that tell if the entity is moving
     */
    public MovingArtificialEntity(Movement movement, Area area, boolean moving) {
        super();
        this.movement = movement;
        this.area = area;
        this.moving = moving;
    }

    /**
     * Gets the area of the moving artificial entity.
     *
     * @return An area where the moving artificial entity can reside.
     */
    public Area getArea() {
        return area;
    }

    /**
     * Gets the area of the moving artificial entity.
     *
     * @param area An area where the moving artificial entity can reside.
     */
    public void setArea(Area area) {
        this.area = area;
    }

    /**
     * Sets the forceMove flag.
     *
     * @param forceMove A flag allowing the moving artificial entity to go outside the area.
     */
    public void setForceMove(boolean forceMove) {
        this.forceMove = forceMove;
    }

    /**
     * Sets the moving flag.
     *
     * @param moving A flag allowing the moving artificial entity to move.
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * Change the orientation and bounces against the bounds of the area.
     */
    private void bounce() {
        movement.setOrientation(rand.nextFloat() * 2 * (float) (Math.PI));
    }

    /**
     * Gets the movement of the moving artificial entity.
     *
     * @return A movement which defines the next position in the 2D plane.
     */
    public Movement getMovement() {
        return movement;
    }

    /**
     * Gets the movement of the moving artificial entity.
     *
     * @param movement A movement which defines the next position in the 2D plane.
     */
    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    @Override
    public void move() {
        CartesianPoint position = movement.nextPosition();
        if (area.isInside(position) || forceMove) {
            movement.setPosition(position);
            super.setLocation(PointConverter.cartesianPointToGeoPoint(position, PlayerManager.getInstance().getCurrentUser().getLocation()));
            //TODO change this
        } else {
            bounce();
        }
    }

    @Override
    public void setLocation(GeoPoint geoPoint) {
        super.setLocation(geoPoint);
        movement.setPosition(PointConverter.geoPointToCartesianPoint(geoPoint));
    }

    @Override
    public boolean isMoving() {
        return moving;
    }

    @Override
    public boolean isOnce() {
        return false;
    }

    @Override
    public void update() {
        if (moving) {
            move();
        }
    }

    @Override
    public CartesianPoint getPosition() {
        return movement.getPosition();
    }

    /**
     * Sets the current position (of the moving artificial entity) in the 2D plan.
     *
     * @param position A position in the 2D plane.
     */
    public void setPosition(CartesianPoint position) {
        movement.setPosition(position);
    }
}
