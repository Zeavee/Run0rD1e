package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.geometry.CartesianPoint;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.PointConverter;

import static org.junit.Assert.assertTrue;

public class PointConverterTest {

    @Test
    public void geoPointToCartesianPointHasPrecisionOfMillimetersForLon0Lat0() {
        GeoPoint geoPoint = new GeoPoint(0, 0);
        CartesianPoint cartesianPoint = PointConverter.geoPointToCartesianPoint(geoPoint);
        GeoPoint geoPointConvertedBack = PointConverter.cartesianPointToGeoPoint(cartesianPoint, geoPoint);
        double distance = geoPoint.distanceTo(geoPointConvertedBack);
        assertTrue(distance <= 0.001);
    }

    @Test
    public void geoPointToCartesianPointHasPrecisionOfMillimetersForLon50Lat50() {
        GeoPoint geoPoint = new GeoPoint(50, 50);
        CartesianPoint cartesianPoint = PointConverter.geoPointToCartesianPoint(geoPoint);
        GeoPoint geoPointConvertedBack = PointConverter.cartesianPointToGeoPoint(cartesianPoint, new GeoPoint(53, 0));
        double distance = geoPoint.distanceTo(geoPointConvertedBack);
        assertTrue(distance <= 0.001);
    }
}
