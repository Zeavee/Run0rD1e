package ch.epfl.sdp.database.firebase.entity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.geometry.GeoPoint;

import static org.junit.Assert.assertEquals;

public class EntityConverterTest {
    @Test
    public void playerConverterTest() {
        UserForFirebase userForFirebase = new UserForFirebase("test@gmail.com", "test", 0);

        Player player = EntityConverter.userForFirebaseToPlayer(userForFirebase);

        PlayerForFirebase playerForFirebase = EntityConverter.playerToPlayerForFirebase(player);

        assertEquals(userForFirebase.getEmail(), playerForFirebase.getEmail());
        assertEquals(userForFirebase.getUsername(), playerForFirebase.getUsername());
    }
}
