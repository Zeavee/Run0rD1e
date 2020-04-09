package ch.epfl.sdp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.GeoPoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PlayerTest {
    private static Player player1 = new Player(6.149290, 46.212470, 50,
            "SkyRiS3s", "test@email.com"); //player position is in Geneva
    private static Player player2 = new Player(5.149290, 30.212470, 40,
            "gamer", "gamer@gmail.com"); //player position
    private static GeoPoint A = new GeoPoint(6.14308, 46.21023);
    private static Healthpack healthpack = new Healthpack(A, false, 25);
    private static Shield shield = new Shield(A, true, 4.5);
    private static Shrinker shrinker = new Shrinker(A, false, 4.5, 10);
    private static Scan scan = new Scan(A, false, 50, new MockMapApi());

    @Test
    public void otherMethodTest() {
        assertTrue(player1.isAlive());
        assertEquals("SkyRiS3s", player1.getUsername());
        assertEquals(0, player1.getSpeed(), 0.001);
        assertEquals(0, player1.getTimeTraveled(), 0.001);
        assertEquals(0, player1.getScore());
        assertEquals(0, player1.getDistanceTraveled(), 0.001);
        assertEquals("test@email.com", player1.getEmail());
    }

    @Test
    public void healthPackUseTest() {
        player1.setHealthPoints(25);
        player1.getInventory().setItemQuantity("Healthpack", 1);
        player1.getInventory().useItem(healthpack);
        assertEquals(50, player1.getHealthPoints(), 0);
    }

    @Test
    public void shieldUseTest() throws InterruptedException {
        player1.getInventory().setItemQuantity("Shield", 1);
        player1.getInventory().useItem(shield);
        assertTrue(player1.isShielded());
        TimeUnit.SECONDS.sleep(5);
        assertFalse(player1.isShielded());
    }

    @Test
    public void shrinkerUseTest() throws InterruptedException {
        Player player2 = new Player(6.149290, 46.212470, 50,
                "SkyRiS3s", "test2@email.com"); //player position is in Geneva
        assertEquals(50, player2.getAoeRadius(), 0);
        player2.getInventory().setItemQuantity("Shrinker", 1);
        player2.getInventory().useItem(shrinker);
        assertEquals(40, player2.getAoeRadius() ,0);
        TimeUnit.SECONDS.sleep(5);
        assertEquals(50, player2.getAoeRadius(), 0);
    }

    @Test
    public void scanTest() {
        Player player2 = new Player(6.149290, 46.212470, 50,
                "SkyRiS3s", "test2@email.com"); //player position is in Geneva
        player2.getInventory().setItemQuantity("Scan", 1);
        player2.getInventory().useItem(scan);
        assertEquals(0, player2.getInventory().getItems().get("Scan"), 0);
    }

    @Test
    public void getEntityTypeReturnsUser() {
        Displayable currentPlayer = new Player(0,0,0,"temp", "fake");
        assertEquals(EntityType.USER, currentPlayer.getEntityType());
    }

    @Test
    public void addItemToInventory() {
        player1.getInventory().setItemQuantity("Healthpack", 1);
        player1.getInventory().addItem("Healthpack");
        assertEquals(2, player1.getInventory().getItems().get("Healthpack"), 0);
    }

    @Test
    public void removeItem() {
        player1.getInventory().setItemQuantity("Healthpack", 6);
        player1.getInventory().removeItem("Healthpack");
        assertEquals(5, player1.getInventory().getItems().get("Healthpack"), 0);
    }

    @Test
    public void playerIsAlive(){
        player2.setAlive(true);
        assertTrue(player2.isAlive());
        assertEquals("gamer", player2.getUsername());
        assertEquals("gamer@gmail.com", player2.getEmail());
    }
    @Test
    public void playerIsNotAlive(){
        player2.setAlive(false);
        assertFalse(player2.isAlive());
    }


}
