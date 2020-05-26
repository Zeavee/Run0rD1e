package ch.epfl.sdp.artificial_intelligence;

import ch.epfl.sdp.game.GameThread;
import ch.epfl.sdp.geometry.GeoPoint;

/**
 * The representation of a movement from an initial position in the 2D plane.
 */
public abstract class Movement {
    private float velocity;
    private float acceleration;
    private double orientation;

    /**
     * Creates a movement which begins in a given initial position in the 2D plane.
     */
    public Movement() {
        velocity = 0;
        acceleration = 0;
        orientation = 0;
    }

    /**
     * Gets the current velocity determined by the movement.
     *
     * @return A value representing the velocity of the movement.
     */
    public float getVelocity() {
        return velocity;
    }

    /**
     * Sets the current velocity of the movement.
     *
     * @param velocity The velocity of the movement.
     */
    public void setVelocity(float velocity) {
        this.velocity = velocity/GameThread.FPS;
    }

    /**
     * Gets the current acceleration determined by the movement.
     *
     * @return A value representing the acceleration of the movement.
     */
    public float getAcceleration() {
        return acceleration;
    }

    /**
     * Sets the current acceleration of the movement.
     *
     * @param acceleration The acceleration of the movement.
     */
    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Gets the current orientation determined by the movement.
     *
     * @return A value representing the orientation of the movement.
     */
    public double getOrientation() {
        return orientation;
    }

    /**
     * Sets the current orientation of the movement.
     *
     * @param orientation The orientation of the movement.
     */
    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    /**
     * Returns the next position determined by the movement.
     *
     * @return A position in the 2D plane.
     * @param from
     */
    public abstract GeoPoint nextPosition(GeoPoint from);
}
