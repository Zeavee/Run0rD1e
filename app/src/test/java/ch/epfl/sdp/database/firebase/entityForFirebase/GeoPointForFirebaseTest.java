package ch.epfl.sdp.database.firebase.entityForFirebase;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class GeoPointForFirebaseTest {
    @Test
    public void test(){
        GeoPointForFirebase geoPointForFirebase = new GeoPointForFirebase(22,22);

        assertEquals(22, geoPointForFirebase.getLatitude());
        assertEquals(22, geoPointForFirebase.getLongitude());

        geoPointForFirebase.setLatitude(10);
        geoPointForFirebase.setLongitude(10);

        assertEquals(10, geoPointForFirebase.getLatitude());
        assertEquals(10, geoPointForFirebase.getLongitude());
    }
}
