package ch.epfl.sdp.geometry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeoPointTest {
    private final static GeoPoint A = new GeoPoint(6.14308, 46.21023);
    private final static GeoPoint B = new GeoPoint(6.56599, 46.52224);
    private final static GeoPoint C = new GeoPoint(7.44428, 46.94652);
    private final static GeoPoint D = new GeoPoint(9.34324, 47.24942);
    private final static GeoPoint E = new GeoPoint(8.86598, 46.10386);

    @Test
    public void distanceToTesting() {
        assertEquals(226_000, B.distanceTo(D), 10);
        assertEquals( 81_890, B.distanceTo(C), 10);
        assertEquals(143_560, C.distanceTo(E), 10);
        assertEquals(269_870, D.distanceTo(A), 10);
    }

    @Test
    public void otherMethodTest() {
        assertEquals(6.14308, A.getLongitude(), 0.01);
        assertEquals(46.21023, A.getLatitude(), 0.01);
        assertEquals(A.getLongitude(), 6.14308, 0.01);
        assertEquals(A.getLatitude(), 46.21023, 0.01);
    }
}
