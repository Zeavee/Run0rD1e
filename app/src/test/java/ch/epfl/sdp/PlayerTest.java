package ch.epfl.sdp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.GeoPoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PlayerTest {
    private static Player player1 = new Player(6.149290, 46.212470, 50,
            "Skyris", "test@email.com"); //player position is in Geneva
    private static Enemy enemy1 = new Enemy(6.568390, 46.520730, 50); //enemy1's position is at EPFL
    private static Enemy enemy2 = new Enemy(6.149596,46.212437, 50); //enemy2's position is close to player1
    private static ArrayList<Enemy> enemyArrayList = new ArrayList<Enemy>();
    private static GeoPoint A = new GeoPoint(6.14308, 46.21023);
    private static Healthpack healthpack = new Healthpack(A, false, 25);
    private static Shield shield = new Shield(A, true, 4.5);
    private static Shrinker shrinker = new Shrinker(A, false, 4.5, 10);


    @Test
    public void updateHealthTest() {
        enemyArrayList.add(enemy1);
        player1.updateHealth(enemyArrayList);
        assertEquals(50, player1.getHealthPoints(), 0);
        enemyArrayList.add(enemy2);
        player1.updateHealth(enemyArrayList);
        assertFalse(player1.getHealthPoints() >= 100);
    }

    @Test
    public void otherMethodTest() {
        assertTrue(player1.isAlive());
        assertEquals("Skyris", player1.getUsername());
        assertEquals(0, player1.getSpeed(), 0.001);
        assertEquals(0, player1.getTimeTraveled(), 0.001);
        assertEquals(0, player1.getScore());
        assertEquals(0, player1.getDistanceTraveled(), 0.001);
        assertEquals("test@email.com", player1.getEmail());
    }

    @Test
    public void healthPackUseTest() {
        player1.setHealthPoints(25);
        player1.setItemInventory("Healthpack", 1);
        player1.useItem(healthpack);
        assertEquals(50, player1.getHealthPoints(), 0);
    }

    @Test
    public void shieldUseTest() throws InterruptedException {
        player1.setItemInventory("Shield", 1);
        player1.useItem(shield);
        assertTrue(player1.isShielded());
        TimeUnit.SECONDS.sleep(5);
        assertFalse(player1.isShielded());
    }

    @Test
    public void shrinkerUseTest() throws InterruptedException {
        Player player2 = new Player(6.149290, 46.212470, 50,
                "SkyRiS3s", "test2@email.com"); //player position is in Geneva
        assertEquals(50, player2.getAoeRadius(), 0);
        player2.setItemInventory("Shrinker", 1);
        player2.useItem(shrinker);
        assertEquals(40, player2.getAoeRadius() ,0);
        TimeUnit.SECONDS.sleep(5);
        assertEquals(50, player2.getAoeRadius(), 0);
    }

    @Test
    public void getEntityTypeReturnsUser() {
        Displayable currentPlayer = new Player(0,0,0,"temp", "fake");
        assertEquals(EntityType.USER, currentPlayer.getEntityType());
    }

    @Test
    public void addItemToInventory() {
        player1.setItemInventory("Healthpack", 1);
        player1.addItemInInventory("Healthpack");
        assertEquals(2, player1.getItemInventory().get("Healthpack"), 0);
    }

    @Test
    public void removeItem() {
        player1.setItemInventory("Healthpack", 6);
        player1.removeItemInInventory("Healthpack");
        assertEquals(5, player1.getItemInventory().get("Healthpack"), 0);
    }
}
