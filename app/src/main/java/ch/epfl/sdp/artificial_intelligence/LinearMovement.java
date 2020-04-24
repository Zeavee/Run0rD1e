package ch.epfl.sdp.artificial_intelligence;

import ch.epfl.sdp.geometry.CartesianPoint;
import ch.epfl.sdp.geometry.Vector;

/**
 * Represent a linear evolution of a movement.
 */
public class LinearMovement extends Movement {
    /**
     * Creates a movement which is linear.
     *
     * @param initialPosition The position in the 2D plane where the movement begins.
     */
    public LinearMovement(CartesianPoint initialPosition) {
        super(initialPosition);
    }

    @Override
    public CartesianPoint nextPosition() {
        Vector vector = Vector.fromPolar(getVelocity(), getOrientation());
        return getPosition().asOriginTo(vector);
    }
}
