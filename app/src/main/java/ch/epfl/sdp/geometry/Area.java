package ch.epfl.sdp.geometry;

/**
 * Represents an area in the 2D plane.
 */
public interface Area {
    boolean isInside(Vector vector);
}
