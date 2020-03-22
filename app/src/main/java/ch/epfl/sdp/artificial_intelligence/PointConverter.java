package ch.epfl.sdp.artificial_intelligence;

import ch.epfl.sdp.map.GeoPoint;
import de.jotschi.geoconvert.GeoConvert;

public class PointConverter {
    public static GenPoint GeoPointToGenPoint(GeoPoint geoPoint) throws Exception {
        double[] xy = GeoConvert.toUtm(geoPoint.longitude(), geoPoint.latitude());
        return new CartesianPoint(xy[0], xy[1]);
    }

    public static GeoPoint GenPointToGeoPoint(GenPoint genPoint, GeoPoint refGeoPoint) {
        double[] latLong = {0, 0};
        CartesianPoint cartPoint = genPoint.toCartesian();
        int zone = (int) Math.round((refGeoPoint.longitude() + 180) / 6.0) + 1;
        boolean north = refGeoPoint.latitude() >= 0;
        GeoConvert.UTMXYToLatLon(cartPoint.arg1, cartPoint.arg2, zone, north, latLong);
        return new GeoPoint(latLong[1], latLong[0]);
    }
}
