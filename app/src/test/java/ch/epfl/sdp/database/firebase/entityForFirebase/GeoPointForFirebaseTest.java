package ch.epfl.sdp.database.firebase.entityForFirebase;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class GeoPointForFirebaseTest {
    @Test
    public void test(){
        GeoPointForFirebase geoPointForFirebase0 = new GeoPointForFirebase();
        GeoPointForFirebase geoPointForFirebase = new GeoPointForFirebase(22,22);

        assertTrue(geoPointForFirebase.getLatitude() == 22);
        assertTrue(geoPointForFirebase.getLongitude() == 22);

        geoPointForFirebase.setLatitude(10);
        geoPointForFirebase.setLongitude(10);

        assertTrue(geoPointForFirebase.getLatitude() == 10);
        assertTrue(geoPointForFirebase.getLongitude() == 10);
    }
}
