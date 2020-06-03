package ch.epfl.sdp.geometry;

import uk.me.jstott.jcoord.LatLng;
import uk.me.jstott.jcoord.UTMRef;

import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;


/**
 * Class GeoPoint: Represents a point on the surface of the Earth
 */
public final class GeoPoint {
    private final double longitude;
    private final double latitude;

    /**
     * A constructor for the GeoPoint
     *
     * @param longitude the longitude of the location
     * @param latitude  the latitude of the location
     */
    public GeoPoint(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * This method gets the longitude of the location
     *
     * @return the longitude of the location
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * This method gets the latitude of the location
     *
     * @return the latitude of the location
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Method which computes the distance in meters between two GeoPoints
     *
     * @param that is a point on Earth
     * @return distance in meters between two points
     */
    public double distanceTo(GeoPoint that) {
        double lambda1 = toRadians(this.longitude);
        double phi1 = toRadians(this.latitude);
        double lambda2 = toRadians(that.longitude);
        double phi2 = toRadians(that.latitude);
        double alpha = 2.0 * asin(sqrt(haversin(phi1 - phi2) + cos(phi1)
                * cos(phi2) * haversin(lambda1 - lambda2)));

        return toMeters(alpha);
    }

    /**
     * Method which computes haversin(x)
     *
     * @param x abscissa axis
     * @return [sin(x / 2)]^2
     */
    private double haversin(double x) {
        double y = sin(x / 2);
        return y * y;
    }

    /**
     * Method which converts radians to meters
     *
     * @param distanceInRadians the distance in radians
     * @return the corresponding distance in meters
     */
    private double toMeters(double distanceInRadians) {
        double earth_radius = 6371000;
        return distanceInRadians * earth_radius;
    }

    /**
     * Gets the point's x coordinate.
     *
     * @return A value representing the x coordinate.
     */
    public double getX() {
        UTMRef utm = geoPointToUTMRef(this);
        return utm.getEasting();
    }

    /**
     * Gets the point's y coordinate.
     *
     * @return A value representing the y coordinate.
     */
    public double getY() {
        UTMRef utm = geoPointToUTMRef(this);
        return utm.getNorthing();
    }

    /**
     * Take this GeoPoint as origin to a vector, which will create a new GeoPoint
     * based on that vector.
     *
     * @param vector The vector which uses this GeoPoint as origin.
     * @return A point on the geodesic surface.
     */
    public GeoPoint asOriginTo(Vector vector) {
        return utmToGeoPoint(getX() + vector.x(), getY() + vector.y(), this);
    }

    /**
     * Transform this point into a vector with origin (0,0).
     *
     * @return A vector representation of this point.
     */
    public Vector toVector() {
        return new Vector(getX(), getY());
    }

    /**
     * Converts a point in the geodesic surface into a point in the plane.
     * @param geoPoint The point in the geodesic surface.
     * @return A point in the plane.
     */
    private UTMRef geoPointToUTMRef(GeoPoint geoPoint) {
        LatLng laln = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
        return laln.toUTMRef();
    }

    /**
     * Converts a point in the plane into a point in the geodesic surface.
     * @param x The abscissa axis.
     * @param y The ordinate axis.
     * @param refGeoPoint The reference point in the geodesic surface, needs to be close to actual
     *                    location of the device.
     * @return A point in the geodesic surface.
     */
    private GeoPoint utmToGeoPoint(double x, double y, GeoPoint refGeoPoint) {
        int lngZone = (int) Math.floor((refGeoPoint.getLongitude() + 180) / 6.0) + 1;
        char latZone = UTMRef.getUTMLatitudeZoneLetter(refGeoPoint.getLatitude());
        UTMRef utm = new UTMRef(lngZone, latZone, x, y);
        LatLng laln = utm.toLatLng();
        return new GeoPoint(laln.getLongitude(), laln.getLatitude());
    }
}
