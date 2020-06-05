package ch.epfl.sdp.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Healthpack;
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
        player1Name = "Test Name";
        player1Email = "test@email.com";
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

        PlayerManager.getInstance().getCurrentUser().status.setHealthPoints(10, PlayerManager.getInstance().getCurrentUser());
        healthpack.useOn(PlayerManager.getInstance().getCurrentUser());

        assertTrue(PlayerManager.getInstance().getCurrentUser().status.getHealthPoints() == 11);
    }
}
