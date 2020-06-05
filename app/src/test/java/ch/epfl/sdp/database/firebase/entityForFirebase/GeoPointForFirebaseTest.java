package ch.epfl.sdp.database.firebase.entityForFirebase;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GeoPointForFirebaseTest {
    @Test
    public void test(){
        GeoPointForFirebase geoPointForFirebase = new GeoPointForFirebase(22,22);

        assertEquals(22, geoPointForFirebase.getLatitude(), 0.01);
        assertEquals(22, geoPointForFirebase.getLongitude(), 0.01);

        geoPointForFirebase.setLatitude(10);
        geoPointForFirebase.setLongitude(10);

        assertEquals(10, geoPointForFirebase.getLatitude(), 0.01);
        assertEquals(10, geoPointForFirebase.getLongitude(), 0.01);
    }
}
