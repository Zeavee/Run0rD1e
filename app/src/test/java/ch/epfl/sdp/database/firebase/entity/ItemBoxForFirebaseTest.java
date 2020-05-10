package ch.epfl.sdp.database.firebase.entity;

import org.junit.Test;

import ch.epfl.sdp.database.firebase.GeoPointForFirebase;
import ch.epfl.sdp.geometry.GeoPoint;

import static org.junit.Assert.*;

public class ItemBoxForFirebaseTest {
    @Test
    public void itemBoxForFirebaseTest() {
        ItemBoxForFirebase itemBoxForFirebase = new ItemBoxForFirebase("id", new GeoPointForFirebase(0,0), false);
        ItemBoxForFirebase itemBoxForFirebase1 = new ItemBoxForFirebase();

        itemBoxForFirebase1.setId("itembox0");
        assertEquals("itembox0", itemBoxForFirebase1.getId());

        itemBoxForFirebase1.setTaken(false);
        assertFalse(itemBoxForFirebase1.isTaken());

        itemBoxForFirebase1.setLocation(new GeoPointForFirebase(33,33));
        assertEquals(33, itemBoxForFirebase1.getLocation().getLatitude(), 0.01);
        assertEquals(33, itemBoxForFirebase1.getLocation().getLongitude(), 0.01);
    }
}
