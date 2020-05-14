package ch.epfl.sdp.geometry;


import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * Represents a mathematical 2D vector.
 */
public class Vector {
    private final double x;
    private final double y;

    /**
     * Create a vector with the given x and y coordinates.
     *
     * @param x The vector's x coordinate.
     * @param y The vector's y coordinate.
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a vector from the polar coordinates.
     *
     * @param magnitude The vector's magnitude.
     * @param direction The vector's direction.
     * @return A vector created from polar coordinates.
     */
    public static Vector fromPolar(double magnitude, double direction) {
        return new Vector(magnitude * cos(direction), magnitude * sin(direction));
    }

    /**
     * Gets the vector's x coordinate.
     *
     * @return A value corresponding to vector's x coordinate.
     */
    public double x() {
        return x;
    }

    /**
     * Gets the vector's y coordinate.
     *
     * @return A value corresponding to vector's y coordinate.
     */
    public double y() {
        return y;
    }

    /**
     * Gets the vector's magnitude.
     *
     * @return A value corresponding to vector's magnitude.
     */
    public double magnitude() {
        return sqrt(x * x + y * y);
    }

    /**
     * Gets the vector's direction.
     *
     * @return A value corresponding to vector's direction in radians.
     */
    public double direction() {
        return atan2(y, x);
    }

    /**
     * Add another vector to this vector.
     *
     * @param that The vector to be added.
     * @return A vector that represents the sum between the two vectors.
     */
    public Vector add(Vector that) {
        return new Vector(x + that.x(), y + that.y());
    }

    /**
     * Subtract another vector to this vector.
     *
     * @param that The vector to be subtracted.
     * @return A vector that represents the difference between the two vectors.
     */
    public Vector subtract(Vector that) {
        return new Vector(that.x() - x, that.y() - y);
    }

    /**
     * Multiply this vector with a scalar.
     *
     * @param scalar The scalar value to be multiplied.
     * @return A vector that represents the multiplication between a vector and a scalar.
     */
    public Vector multiplyByScalar(double scalar) {
        return fromPolar(magnitude() * scalar, direction());
    }

    /**
     * Normalize the vector.
     *
     * @return A vector that represents the normalized version on this vector.
     */
    public Vector normalize() {
        return new Vector(x / magnitude(), y / magnitude());
    }

    /**
     * Return the vector perpendicular to this vector.
     *
     * @return A vector that represents the perpendicular version on this vector.
     */
    public Vector perpendicular() {
        return new Vector(-y, x);
    }

    public double norm() {
        return Math.sqrt(x*x + y*y);
    }

    /**
     * Invert the vector's direction.
     *
     * @return A vector that represents the inverted version on this vector.
     */
    public Vector invertDirection() {
        return multiplyByScalar(-1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o.getClass() == this.getClass()) {
            Vector vector = (Vector) o;

            if (x == vector.x && y == vector.y) {
                return true;
            }
        }

        return false;
    }
}
