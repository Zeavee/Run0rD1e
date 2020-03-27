package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.GenPoint;
import ch.epfl.sdp.artificial_intelligence.PointConverter;
import ch.epfl.sdp.map.GeoPoint;

import static org.junit.Assert.assertTrue;

public class PointConverterTest {

    @Test
    public void geoPointToGenPointHasPrecisionOfMillimetersForLon0Lat0() {
        GeoPoint geoPoint = new GeoPoint(0, 0);
        GenPoint genPoint = PointConverter.GeoPointToGenPoint(geoPoint);
        GeoPoint geoPointConvertedBack = PointConverter.GenPointToGeoPoint(genPoint, geoPoint);
        double distance = geoPoint.distanceTo(geoPointConvertedBack);
        assertTrue(distance <= 0.001);
    }

    @Test
    public void geoPointToGenPointHasPrecisionOfMillimetersForLon50Lat50() {
        GeoPoint geoPoint = new GeoPoint(50, 50);
        GenPoint genPoint = PointConverter.GeoPointToGenPoint(geoPoint);
        GeoPoint geoPointConvertedBack = PointConverter.GenPointToGeoPoint(genPoint, new GeoPoint(53, 0));
        double distance = geoPoint.distanceTo(geoPointConvertedBack);
        assertTrue(distance <= 0.001);
    }
}
