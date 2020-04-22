package ch.epfl.sdp.database.firebase.entity;

import org.junit.Test;

import ch.epfl.sdp.entity.Player;

import static org.junit.Assert.assertEquals;

public class EntityConverterTest {
    @Test
    public void entityConverterTest() {
        UserForFirebase userForFirebase = new UserForFirebase("test@gmail.com", "test", 0.0);

        Player player = EntityConverter.UserForFirebaseToPlayer(userForFirebase);

        PlayerForFirebase playerForFirebase = EntityConverter.PlayerToPlayerForFirebase(player);

        assertEquals(userForFirebase.getEmail(), playerForFirebase.getEmail());
        assertEquals(userForFirebase.getUsername(), playerForFirebase.getUsername());
    }
}
