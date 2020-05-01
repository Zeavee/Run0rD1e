package ch.epfl.sdp.entity;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.utils.MockMapApi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PlayerTest {
    private Player player1; //player position is in Geneva
    private Game game;

    @Before
    public void setup(){
        PlayerManager playerManager = new PlayerManager();
        player1 = new Player(6.149290, 46.212470, 50, "Skyris", "test@email.com");
        game.getInstance().setMapApi(new MockMapApi());
        game.getInstance().setRenderer(displayables -> {

        });
        PlayerManager.setCurrentUser(player1);
    }

    @Test
    public void otherMethodTest() {
        assertTrue(player1.isAlive());
        assertEquals("Skyris", player1.getUsername());
        assertEquals(0, player1.getScore(), 0);
        assertEquals(0, player1.getSpeed(), 0.001);
        assertEquals(0, player1.getTimeTraveled(), 0.001);
        assertEquals(0, player1.getGeneralScore());
        assertEquals(0, player1.getDistanceTraveled(), 0.001);
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
    public void scoreIncreasesOnDisplacementWithTime() throws InterruptedException {
        assertEquals(0, player1.generalScore);
        assertEquals(0, player1.currentGameScore);
        Game.getInstance().initGame();
        Thread.sleep(11000);
        assertEquals(10, player1.currentGameScore);
        player1.distanceTraveled += 5000;
        Thread.sleep(10000);
        assertEquals(30, player1.currentGameScore);
        Game.getInstance().destroyGame();
        Thread.sleep(10000);
        assertEquals(80, player1.generalScore);
        assertEquals(0, player1.currentGameScore);
    }
}
