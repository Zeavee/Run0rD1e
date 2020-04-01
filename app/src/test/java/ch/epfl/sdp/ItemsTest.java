package ch.epfl.sdp;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;
import ch.epfl.sdp.map.GeoPoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class ItemsTest {
    private PlayerManager playerManager;
    private Player player;
    private GeoPoint A;
    private Healthpack healthpack;
    private Shield shield;
    private Shrinker shrinker;
    private Scan scan;

    @Before
    public void setup(){
        playerManager = new PlayerManager();
        player = new Player();
        PlayerManager.addPlayer(player);
        A = new GeoPoint(6.14308, 46.21023);
        healthpack = new Healthpack(A,false, 60);
        shield = new Shield(A, false, 40,player);
        shrinker = new Shrinker(A, true, 40, 10,player);
        scan = new Scan(A, false, 50, new MockMapApi());
    }

    @Test
    public void healthpackTest() {
        assertFalse(healthpack.isTaken());
        assertEquals(60.0, healthpack.getHealthPackAmount(), 0);
    }

    @Test
    public void shieldTest() {
        assertFalse(shield.isTaken());
        assertEquals(40, shield.getRemainingTime(), 0);
        shield.takeItem();
        assertTrue(shield.isTaken());
    }

    @Test
    public void shrinkerTest() {
        assertTrue(shrinker.isTaken());
        assertEquals(40, shrinker.getRemainingTime(), 0);
        assertEquals(10, shrinker.getShrinkingRadius(), 0);
    }


    @Test
    public void increaseHealth() {
        Player player1 = new Player(6.149290, 46.212470, 50,
                "Skyris", "test@email.com"); //player position is in Geneva
        player1.setHealthPoints(30);
        healthpack.increaseHealthPlayer(player1, 100);
        assertEquals(90, player1.getHealthPoints(), 0);
    }

    @Test
    public void scanTest() {
        String a = scan.getDescription();
        assertEquals("Item that scans the entire map and reveals other players for a short delay", a);
    }

}
