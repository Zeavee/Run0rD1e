package ch.epfl.sdp.artificial_intelligence;

import ch.epfl.sdp.map.GeoPoint;
import uk.me.jstott.jcoord.LatLng;
import uk.me.jstott.jcoord.UTMRef;

public class PointConverter {
    /**
     * Get a GenPoint from a GeoPoint
     *
     * @param geoPoint a point on a geodesic surface
     * @return a point transformed from the geodesic surface to the plane
     */
    public static GenPoint GeoPointToGenPoint(GeoPoint geoPoint) {
        LatLng laln = new LatLng(geoPoint.latitude(), geoPoint.longitude());
        UTMRef utm = laln.toUTMRef();
        return new CartesianPoint(utm.getEasting(), utm.getNorthing());
    }

    /**
     * Get a GeoPoint from a GenPoint
     * @param genPoint point in the plane (unit in meters)
     * @param refGeoPoint reference point on the surface of earth, must be close to the actual
     *                    playing location
     * @return a point transformed from the plane to a geodesic surface
     */
    public static GeoPoint GenPointToGeoPoint(GenPoint genPoint, GeoPoint refGeoPoint) {
        int lngZone = (int) Math.floor((refGeoPoint.longitude() + 180) / 6.0) + 1;
        char latZone = UTMRef.getUTMLatitudeZoneLetter(refGeoPoint.latitude());
        CartesianPoint cp = genPoint.toCartesian();
        UTMRef utm = new UTMRef(lngZone, latZone, cp.arg1, cp.arg2);
        LatLng laln = utm.toLatLng();
        return new GeoPoint(laln.getLongitude(), laln.getLatitude());
    }
}
