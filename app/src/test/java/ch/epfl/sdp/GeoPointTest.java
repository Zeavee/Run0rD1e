package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.map.GeoPoint;

import static org.junit.Assert.assertEquals;

public class GeoPointTest {
    private static GeoPoint A = new GeoPoint(6.14308, 46.21023);
    private static GeoPoint B = new GeoPoint(6.56599, 46.52224);
    private static GeoPoint C = new GeoPoint(7.44428, 46.94652);
    private static GeoPoint D = new GeoPoint(9.34324, 47.24942);
    private static GeoPoint E = new GeoPoint(8.86598, 46.10386);

    @Test
    public void distanceToTesting() {
        assertEquals(226_000, B.distanceTo(D), 10);
        assertEquals( 81_890, B.distanceTo(C), 10);
        assertEquals(143_560, C.distanceTo(E), 10);
        assertEquals(269_870, D.distanceTo(A), 10);
    }

    @Test
    public void otherMethodTest() {
        assertEquals(6.14308, A.longitude(), 0.01);
        assertEquals(46.21023, A.latitude(), 0.01);
        assertEquals(A.longitude(), 6.14308, 0.01);
        assertEquals(A.latitude(), 46.21023, 0.01);
    }
}
