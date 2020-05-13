package ch.epfl.sdp.geometry;

/**
 * This interface should be implemented by all classes that represents something having a position in the game,
 * as such you can access it's position in a 2D plane.
 */
public interface Positionable {
    /**
     * Gets the position in a 2D plane.
     *
     * @return The position in a 2D plane.
     */
    GeoPoint getLocation();
}
