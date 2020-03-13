package ch.epfl.sdp;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class ItemsTest {
    private static GeoPoint A = new GeoPoint(6.14308, 46.21023);
    private static Healthpack healthpack = new Healthpack(A,false, 60);
    private static Shield shield = new Shield(A, false, 40);
    private static Shrinker shrinker = new Shrinker(A, true, 40, 10);
    private static Scan scan = new Scan(A, false, 40);

    @Test
    public void healthpackTest() {
        assertFalse(healthpack.isTaken());
        assertEquals(60.0, healthpack.getHealthPackAmount(), 0);
    }

    @Test
    public void shieldTest() {
        assertFalse(shield.isTaken());
        assertEquals(40, shield.getShieldTime(), 0);

    }

}
