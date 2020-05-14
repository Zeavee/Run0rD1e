package ch.epfl.sdp.geometry;

import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

import ch.epfl.sdp.entity.PlayerManager;
import uk.me.jstott.jcoord.LatLng;
import uk.me.jstott.jcoord.UTMRef;


/**
 * Class GeoPoint: Represents a point on the surface of the Earth
 */
public final class GeoPoint {
    /**
     * Constant value which represents the length of Earth's radius (in meters)
     */
    private final double EARTH_RADIUS = 6371000;
    private final double longitude;
    private final double latitude;

    public GeoPoint(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    /**
     * Method which computes the distance in meters between two GeoPoints
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
        return new Vector(getX(),getY());
    }

    private UTMRef geoPointToUTMRef(GeoPoint geoPoint) {
        LatLng laln = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
        UTMRef utm = laln.toUTMRef();
        return utm;
    }

    private GeoPoint utmToGeoPoint(double x, double y, GeoPoint refGeoPoint) {
        int lngZone = (int) Math.floor((refGeoPoint.getLongitude() + 180) / 6.0) + 1;
        char latZone = UTMRef.getUTMLatitudeZoneLetter(refGeoPoint.getLatitude());
        UTMRef utm = new UTMRef(lngZone, latZone, x, y);
        LatLng laln = utm.toLatLng();
        return new GeoPoint(laln.getLongitude(), laln.getLatitude());
    }
}
