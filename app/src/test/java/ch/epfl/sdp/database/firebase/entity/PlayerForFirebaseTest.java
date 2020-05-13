package ch.epfl.sdp.database.firebase.entity;

import org.junit.Test;

import ch.epfl.sdp.database.firebase.GeoPointForFirebase;
import ch.epfl.sdp.geometry.GeoPoint;

import static org.junit.Assert.assertEquals;

public class PlayerForFirebaseTest {
    @Test
    public void playerForFirebaseTest() {
        PlayerForFirebase playerForFirebase1 = new PlayerForFirebase();

        playerForFirebase1.setEmail("user1@gmail.com");
        assertEquals("user1@gmail.com", playerForFirebase1.getEmail());

        playerForFirebase1.setUsername("user1");
        assertEquals("user1", playerForFirebase1.getUsername());

        playerForFirebase1.setAoeRadius(9.9);
        assertEquals(9.9, playerForFirebase1.getAoeRadius(), 0.01);

        playerForFirebase1.setHealthPoints(100);
        assertEquals(100.0, playerForFirebase1.getHealthPoints(), 0.01);


        playerForFirebase1.setGeoPointForFirebase(new GeoPointForFirebase(33,33));
        assertEquals(33, playerForFirebase1.getGeoPointForFirebase().getLatitude(), 0.01);
        assertEquals(33, playerForFirebase1.getGeoPointForFirebase().getLongitude(), 0.01);
    }
}
