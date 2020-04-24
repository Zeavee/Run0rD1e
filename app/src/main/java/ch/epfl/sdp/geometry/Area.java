package ch.epfl.sdp.geometry;

/**
 * Represents an area in the 2D plane.
 */
public interface Area {
    /**
     * This method should return true if and only if the point is inside the area.
     *
     * @param cartesianPoint The point in the 2D plane to be checked.
     * @return True if and only if the point is inside the area.
     */
    boolean isInside(CartesianPoint cartesianPoint);
}
