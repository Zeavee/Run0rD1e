package ch.epfl.sdp.database.firebase.entity;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.geometry.GeoPoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ItemsForFirebaseTest {
    @Test
    public void itemsForFirebaseTest() {
        ItemsForFirebase itemsForFirebase = new ItemsForFirebase();

        itemsForFirebase.setDate(new Date(22));
        assertEquals(new Date(22).toString(), itemsForFirebase.getDate().toString());

        Map<String, Integer> itemsMap = new HashMap<>();
        itemsMap.put("healthPack", 1);
        itemsMap.put("scan", 2);
        itemsForFirebase.setItemsMap(itemsMap);
        assertEquals(1, (int) itemsForFirebase.getItemsMap().get("healthPack"));
        assertEquals(2, (int) itemsForFirebase.getItemsMap().get("scan"));

        ItemsForFirebase itemsForFirebase1 = new ItemsForFirebase(itemsMap, new Date(System.currentTimeMillis()));
        assertEquals(2, itemsForFirebase1.getItemsMap().size());
    }
}
