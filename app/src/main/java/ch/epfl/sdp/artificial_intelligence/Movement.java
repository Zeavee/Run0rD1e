package ch.epfl.sdp.artificial_intelligence;

import ch.epfl.sdp.geometry.CartesianPoint;

/**
 * The representation of a movement from an initial position in the 2D plane.
 */
public abstract class Movement {
    private CartesianPoint position;
    private float velocity;
    private float acceleration;
    private double orientation;

    /**
     * Creates a movement which begins in a given initial position in the 2D plane.
     *
     * @param initialPosition
     */
    public Movement(CartesianPoint initialPosition) {
        position = initialPosition;
        velocity = 1;
        acceleration = 0;
        orientation = 0;
    }

    /**
     * Gets the current position determined by the movement.
     *
     * @return A position in the 2D plane.
     */
    public CartesianPoint getPosition() {
        return position;
    }

    /**
     * Sets the current position of the movement.
     *
     * @param position The position in the 2D plane.
     */
    public void setPosition(CartesianPoint position) {
        this.position = position;
    }

    /**
     * Gets the current velocity determined by the movement.
     * @return A value representing the velocity of the movement.
     */
    public float getVelocity() {
        return velocity;
    }

    /**
     * Sets the current velocity of the movement.
     * @param velocity The velocity of the movement.
     */
    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the current acceleration determined by the movement.
     * @return A value representing the acceleration of the movement.
     */
    public float getAcceleration() {
        return acceleration;
    }

    /**
     * Sets the current acceleration of the movement.
     * @param acceleration The acceleration of the movement.
     */
    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Gets the current orientation determined by the movement.
     * @return A value representing the orientation of the movement.
     */
    public double getOrientation() {
        return orientation;
    }

    /**
     * Sets the current orientation of the movement.
     * @param orientation The orientation of the movement.
     */
    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    /**
     * Returns the next position determined by the movement.
     *
     * @return A position in the 2D plane.
     */
    public abstract CartesianPoint nextPosition();
}
