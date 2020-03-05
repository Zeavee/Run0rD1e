package ch.epfl.sdp;

import org.junit.Test;

import static java.lang.Math.toRadians;
import static org.junit.Assert.assertEquals;

public class GeoPointTest {
    private static GeoPoint A = new GeoPoint(toRadians(6.14308), toRadians(46.21023));
    private static GeoPoint B = new GeoPoint(toRadians(6.56599), toRadians(46.52224));
    private static GeoPoint C = new GeoPoint(toRadians(7.44428), toRadians(46.94652));
    private static GeoPoint D = new GeoPoint(toRadians(9.34324), toRadians(47.24942));
    private static GeoPoint E = new GeoPoint(toRadians(8.86598), toRadians(46.10386));

    @Test
    public void distanceToTesting() {
        assertEquals(226_000, B.distanceTo(D), 10);
        assertEquals( 81_890, B.distanceTo(C), 10);
        assertEquals(143_560, C.distanceTo(E), 10);
        assertEquals(269_870, D.distanceTo(A), 10);
    }
}
