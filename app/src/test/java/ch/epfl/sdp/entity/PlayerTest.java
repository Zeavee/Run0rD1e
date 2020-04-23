package ch.epfl.sdp.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.map.Displayable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PlayerTest {
    private Player player1; //player position is in Geneva

    @Before
    public void setup(){
        PlayerManager playerManager = new PlayerManager();
        player1 = new Player(6.149290, 46.212470, 50, "Skyris", "test@email.com");

        PlayerManager.setCurrentUser(player1);
    }

    @Test
    public void otherMethodTest() {
        assertTrue(player1.isAlive());
        assertEquals("Skyris", player1.getUsername());
        assertEquals(0, player1.getScore(), 0);
        assertEquals("test@email.com", player1.getEmail());
    }

    @Test
    public void healthPackUseTest() {
        Healthpack healthpack = new Healthpack(1);

        PlayerManager.getCurrentUser().setHealthPoints(10);
        healthpack.use();

        assertTrue(PlayerManager.getCurrentUser().getHealthPoints() == 11);
    }

    @Test
    public void getEntityTypeReturnsUser() {
        Displayable currentPlayer = new Player(0,0,0,"temp", "fake");
        assertEquals(EntityType.USER, currentPlayer.getEntityType());
    }
}
