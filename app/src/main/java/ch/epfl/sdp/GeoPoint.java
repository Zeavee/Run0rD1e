package ch.epfl.sdp;


import static java.lang.Math.*;
import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;


/**
 * Class GeoPoint: Represents a point on the surface of the Earth
 */
public final class GeoPoint {

    /**
     * Constant value which represents the length of Earth's radius (in meters)
     */
    public static final double EARTH_RADIUS = 6371000;

    public double longitude;
    public double latitude;

    public GeoPoint() {
    }

    public GeoPoint(double longitude, double latitude) {
        this.longitude = toRadians(longitude);
        this.latitude = toRadians(latitude);
    }

    public double longitude() {
        return toDegrees(this.longitude);
    }

    public double latitude() {
        return toDegrees(this.latitude);
    }

    /**
     * Method which computes the distance in meters between two GeoPoints
     * @param that is a point on Earth
     * @return distance in meters between two points
     */
    public double distanceTo(GeoPoint that) {
        double lambda1 = this.longitude;
        double phi1 = this.latitude;
        double lambda2 = that.longitude;
        double phi2 = that.latitude;
        double alpha = 2.0 * asin(sqrt(haversin(phi1 - phi2) + cos(phi1)
                        * cos(phi2) * haversin(lambda1 - lambda2)));

        return toMeters(alpha);
    }

    /**
     * Method which computes haversin(x)
     * @param x
     * @return [sin(x/2)]^2
     */
    private double haversin(double x) {
        double y = sin(x/2);
        return y*y;
    }

    /**
     * Method which converts radians to meters
     * @param distanceInRadians
     * @return the corresponding distance in meters
     */
    private double toMeters(double distanceInRadians) {
        return distanceInRadians * EARTH_RADIUS;
    }
}
