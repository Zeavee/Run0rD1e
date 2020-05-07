package ch.epfl.sdp.database.firebase.entity;

import org.junit.Test;

import ch.epfl.sdp.geometry.GeoPoint;

import static org.junit.Assert.*;

public class ItemBoxForFirebaseTest {
    @Test
    public void itemBoxForFirebaseTest() {
        ItemBoxForFirebase itemBoxForFirebase = new ItemBoxForFirebase();

        itemBoxForFirebase.setId("itembox0");
        assertEquals("itembox0", itemBoxForFirebase.getId());

        itemBoxForFirebase.setTaken(false);
        assertFalse(itemBoxForFirebase.isTaken());

        itemBoxForFirebase.setLocation(new GeoPoint(33,33));
        assertEquals(33, itemBoxForFirebase.getLocation().getLatitude(), 0.01);
        assertEquals(33, itemBoxForFirebase.getLocation().getLongitude(), 0.01);
    }
}
