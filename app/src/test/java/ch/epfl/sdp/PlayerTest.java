package ch.epfl.sdp;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PlayerTest {
    private static Player player1 = new Player(6.149290, 46.212470, 50,
            "Skyris", "test@email.com"); //player position is in Geneva
    private static Enemy enemy1 = new Enemy(6.568390, 46.520730, 50); //enemy1's position is at EPFL
    private static Enemy enemy2 = new Enemy(6.149596,46.212437, 50); //enemy2's position is close to player1
    private static ArrayList<Enemy> enemyArrayList = new ArrayList<Enemy>();

    @Test
    public void updateHealthTest() {
        enemyArrayList.add(enemy1);
        player1.updateHealth(enemyArrayList);
        assertEquals(100, player1.getHealthPoints(), 0);
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
    public void getEntityTypeReturnsUser() {
        Displayable currentPlayer = new Player(0,0,0,"temp", "fake");
        assertEquals(EntityType.USER, currentPlayer.getEntityType());
    }
}
