package ch.epfl.sdp.entities.enemy.artificial_intelligence;

import ch.epfl.sdp.map.location.GeoPoint;
import ch.epfl.sdp.geometry.Vector;

/**
 * Represent a linear evolution of a movement.
 */
public class LinearMovement extends Movement {
    /**
     * Creates a movement which is linear.
     */
    public LinearMovement() {
        super();
    }

    @Override
    public GeoPoint nextPosition(GeoPoint from) {
        Vector vector = Vector.fromPolar(getVelocity(), getOrientation());
        return from.asOriginTo(vector);
    }
}
