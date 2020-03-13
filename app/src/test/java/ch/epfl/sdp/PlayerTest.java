package ch.epfl.sdp;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;
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
    private static Healthpack healthpack = new Healthpack(A, true, 25);
    private static Shield shield = new Shield(A, true, 5);
    private static Shrinker shrinker = new Shrinker(A, false, 5, 10);


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
        player1.addInventory(healthpack);
        player1.useItem(0);
        assertEquals(50, player1.getHealthPoints(), 0);
    }

    public void shieldUseTest() {
        player1.addInventory(shield);
        player1.useItem(1);
        assertEquals(true, player1.isShielded());
        TimerTask testTask = new TimerTask() {
            @Override
            public void run() {
                int dummy = 3;
            }
        };
        Timer timer = new Timer();
        timer.schedule(testTask, 5*1000);
        assertEquals(false, player1.isShielded());
    }

    @Test
    public void shrinkerUseTest() {
        Player player2 = new Player(6.149290, 46.212470, 50,
                "SkyRiS3s", "test2@email.com"); //player position is in Geneva
        player2.addInventory(shrinker);
        player2.useItem(0);
        assertEquals(50.0, player1.getAoeRadius(), 0);
        TimerTask testTask = new TimerTask() {
            @Override
            public void run() {
                int dummy = 3;
            }
        };
        Timer timer = new Timer();
        timer.schedule(testTask, 5*1000);
        assertEquals(50, player1.getAoeRadius(), 0);
    }

}