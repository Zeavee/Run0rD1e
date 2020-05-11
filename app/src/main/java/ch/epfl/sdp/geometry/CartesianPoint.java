package ch.epfl.sdp.geometry;

/**
 * This class represents a mathematical point in the 2D plane in cartesian coordinates.
 */
public class CartesianPoint {
    private final double x;
    private final double y;

    /**
     * Create a point in the 2D plane.
     *
     * @param x The point's x coordinate.
     * @param y The point's y coordinate.
     */
    public CartesianPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the point's x coordinate.
     *
     * @return A value representing the x coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the point's y coordinate.
     *
     * @return A value representing the y coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Gives the distance from a point relative to this point.
     *
     * @param cartesianPoint The point we want to know the distance from.
     * @return the distance from that point.
     */
    public double distanceFrom(CartesianPoint cartesianPoint) {
        return Math.sqrt((cartesianPoint.getX() - x) * (cartesianPoint.getX() - x) + (cartesianPoint.getY() - y) * (cartesianPoint.getY() - y));
    }

    /**
     * Return another point with respect to a vector based on this point as an origin.
     *
     * @param vector The vector to combine to this point.
     * @return A point representing the combination of a vector and this point as origin.
     */
    public CartesianPoint asOriginTo(Vector vector) {
        return new CartesianPoint(getX() + vector.x(), getY() + vector.y());
    }

    /**
     * Transform this point into a vector with origin (0,0).
     *
     * @return A vector representation of this point.
     */
    public Vector toVector() {
        return new Vector(getX(), getY());
    }
}
