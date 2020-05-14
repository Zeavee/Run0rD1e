package ch.epfl.sdp.database.firebase.entity;

import org.junit.Test;

import ch.epfl.sdp.database.firebase.GeoPointForFirebase;

import static junit.framework.TestCase.assertTrue;

public class GeoPointForFirebaseTest {
    @Test
    public void test(){
        GeoPointForFirebase geoPointForFirebase = new GeoPointForFirebase(22,22);

        assertTrue(geoPointForFirebase.getLatitude() == 22);
        assertTrue(geoPointForFirebase.getLongitude() == 22);

        geoPointForFirebase.setLatitude(10);
        geoPointForFirebase.setLongitude(10);

        assertTrue(geoPointForFirebase.getLatitude() == 10);
        assertTrue(geoPointForFirebase.getLongitude() == 10);
    }
}
