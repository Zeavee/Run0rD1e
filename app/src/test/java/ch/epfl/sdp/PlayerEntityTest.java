package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.db.PlayerEntity;

import static org.junit.Assert.assertEquals;

public class PlayerEntityTest {

    @Test
    public void getAndSetEmailTest() {
        PlayerEntity player = new PlayerEntity("peilin@gmail.com", "peilin", 100);
        player.setEmail("kang@gmail.com");
        assertEquals("kang@gmail.com", player.getEmail());
    }

    @Test
    public void getAndSetUsenameTest() {
        PlayerEntity player = new PlayerEntity("peilin@gmail.com", "peilin", 100);
        player.setUsername("kang");
        assertEquals("kang", player.getUsername());
    }

    @Test
    public void getAndSetHealthpointTest() {
        PlayerEntity player = new PlayerEntity("peilin@gmail.com", "peilin", 100);
        player.setHealthpoint(100.0);
        assertEquals(100.0, player.getHealthpoint(), 0.1);
    }
}
