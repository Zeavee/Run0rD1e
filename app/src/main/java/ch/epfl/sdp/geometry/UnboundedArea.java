package ch.epfl.sdp.geometry;

/**
 * Represents an unbounded area. Any point in the 2D plane is inside the area.
 */
public class UnboundedArea implements Area {
    @Override
    public boolean isInside(CartesianPoint cartesianPoint) {
        return true;
    }
}
