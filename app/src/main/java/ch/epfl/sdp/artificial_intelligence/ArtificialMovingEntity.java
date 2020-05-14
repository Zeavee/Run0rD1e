package ch.epfl.sdp.artificial_intelligence;

import java.util.Random;

import ch.epfl.sdp.entity.AoeRadiusEntity;
import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.LocalArea;
import ch.epfl.sdp.geometry.UnboundedArea;

/**
 * Represents an entity of the game that can move automatically by setting a movement. The movement
 * can be limited by setting an area where the entity can reside.
 */
public abstract class ArtificialMovingEntity extends AoeRadiusEntity implements Updatable {
    private Movement movement;
    private LocalArea localArea;
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
    public ArtificialMovingEntity() {
        this(new GeoPoint(0,0), new LinearMovement(),
            new LocalArea(new UnboundedArea(), new GeoPoint(0,0)), true);
    }

    /**
     * Creates a default moving artificial entity, by specifying a movement, an area and if it's
     * already moving.
     *
     * @param movement the type of movement the entity use
     * @param localArea the area the entity can move in
     * @param moving a boolean that tell if the entity is moving
     */
    public ArtificialMovingEntity(GeoPoint location, Movement movement, LocalArea localArea,
                                  boolean moving) {
        super(location);
        this.movement = movement;
        this.localArea = localArea;
        this.moving = moving;
    }

    /**
     * Gets the area of the moving artificial entity.
     *
     * @return An area where the moving artificial entity can reside.
     */
    public LocalArea getLocalArea() {
        return localArea;
    }

    /**
     * Gets the area of the moving artificial entity.
     *
     * @param localArea An area where the moving artificial entity can reside.
     */
    public void setLocalArea(LocalArea localArea) {
        this.localArea = localArea;
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

    public void move() {
        GeoPoint position = movement.nextPosition(getLocation());
        if (localArea.isInside(position) || forceMove) {
            super.setLocation(position);
        } else {
            bounce();
        }
    }

    public boolean isMoving() {
        return moving;
    }

    @Override
    public void update() {
        if (moving) {
            move();
        }
    }
}
