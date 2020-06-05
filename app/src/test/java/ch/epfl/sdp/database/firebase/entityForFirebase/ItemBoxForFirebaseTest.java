package ch.epfl.sdp.database.firebase.entityForFirebase;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ItemBoxForFirebaseTest {
    @Test
    public void itemBoxForFirebaseTest() {
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
