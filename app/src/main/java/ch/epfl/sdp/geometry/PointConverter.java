package ch.epfl.sdp.geometry;

import uk.me.jstott.jcoord.LatLng;
import uk.me.jstott.jcoord.UTMRef;

/**
 * This class has utility methods for converting between GeoPoints and GenPoints.
 */
public class PointConverter {
    /**
     * Gets a GenPoint from a GeoPoint.
     *
     * @param geoPoint The point on a geodesic surface.
     * @return A point transformed from the geodesic surface to the plane.
     */
    public static CartesianPoint geoPointToCartesianPoint(GeoPoint geoPoint) {
        LatLng laln = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
        UTMRef utm = laln.toUTMRef();
        return new CartesianPoint(utm.getEasting(), utm.getNorthing());
    }

    /**
     * Gets a GeoPoint from a GenPoint
     *
     * @param cartesianPoint The Point in the plane (unit in meters)
     * @param refGeoPoint    The reference point on the surface of earth, must be close to the actual
     *                       playing location
     * @return A point transformed from the plane to a geodesic surface
     */
    public static GeoPoint cartesianPointToGeoPoint(CartesianPoint cartesianPoint, GeoPoint refGeoPoint) {
        int lngZone = (int) Math.floor((refGeoPoint.getLongitude() + 180) / 6.0) + 1;
        char latZone = UTMRef.getUTMLatitudeZoneLetter(refGeoPoint.getLatitude());
        UTMRef utm = new UTMRef(lngZone, latZone, cartesianPoint.getX(), cartesianPoint.getY());
        LatLng laln = utm.toLatLng();
        return new GeoPoint(laln.getLongitude(), laln.getLatitude());
    }
}
