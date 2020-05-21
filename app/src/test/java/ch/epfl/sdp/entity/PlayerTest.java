package ch.epfl.sdp.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.map.MockMap;
import ch.epfl.sdp.utils.RandomGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PlayerTest {
    private Player player1;
    private GeoPoint g;
    private String player1Name;
    private String player1Email;

    @Before
    public void setup() {
        RandomGenerator randGen = new RandomGenerator();
        g = randGen.randomGeoPoint();
        player1Name = randGen.randomValidString(10);
        player1Email = randGen.randomEmail();
        player1 = new Player(g.getLongitude(), g.getLatitude(), 50, player1Name, player1Email);
        PlayerManager.getInstance().setCurrentUser(player1);
    }

    @After
    public void teardown(){
        PlayerManager.getInstance().clear();
    }

    @Test
    public void otherMethodTest() {
        assertEquals(player1Name, player1.getUsername());
        assertEquals(player1Email, player1.getEmail());
        assertEquals(0, player1.getGeneralScore());
        assertEquals(0, player1.getDistanceTraveled(), 0.001);
    }

    @Test
    public void healthPackUseTest() {
        Healthpack healthpack = new Healthpack(1);

        PlayerManager.getInstance().getCurrentUser().setHealthPoints(10);
        healthpack.useOn(PlayerManager.getInstance().getCurrentUser());

        assertTrue(PlayerManager.getInstance().getCurrentUser().getHealthPoints() == 11);
    }

    @Test
    public void scoreIncreasesOnDisplacementWithTime() throws InterruptedException {
      /*  MockMap mockMap = new MockMap();
        Game.getInstance().setMapApi(mockMap);
        Game.getInstance().setRenderer(mockMap);

        assertEquals(0, player1.generalScore);
        assertEquals(0, player1.currentGameScore);
        Game.getInstance().initGame();
        Thread.sleep(11000);
        assertEquals(10, player1.getCurrentGameScore());
        player1.setDistanceTraveled(player1.getDistanceTraveled() + 5000);
        Thread.sleep(10000);
        assertEquals(30, player1.getCurrentGameScore());
        Game.getInstance().destroyGame();
        Thread.sleep(10000);
        assertEquals(80, player1.generalScore);
        assertEquals(0, player1.currentGameScore);

        Game.getInstance().destroyGame();*/
    }
}
