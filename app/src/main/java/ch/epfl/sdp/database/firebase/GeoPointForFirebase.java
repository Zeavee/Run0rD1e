package ch.epfl.sdp.database.firebase;

/**
 * Simplified version of GeoPoint for cloud database usage
 */
public class GeoPointForFirebase {
    private double longitude;
    private double latitude;

    /**
     * For Firebase each custom class must have a public constructor that takes no arguments.
     */
    public GeoPointForFirebase() {
    }

    /**
     * Construct a GeoPointForFirebase instance
     *
     * @param longitude The longitude of the GeoPointForFirebase
     * @param latitude  The latitude of the GeoPointForFirebase
     */
    public GeoPointForFirebase(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Get the longitude of the GeoPointForFirebase
     *
     * @return The longitude of the GeoPointForFirebase
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Set the longitude of the GeoPointForFirebase
     *
     * @param longitude The longitude of the GeoPointForFirebase
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Get the latitude of the GeoPointForFirebase
     *
     * @return The latitude of the GeoPointForFirebase
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Set the latitude of the GeoPointForFirebase
     *
     * @param latitude The latitude of the GeoPointForFirebase
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
