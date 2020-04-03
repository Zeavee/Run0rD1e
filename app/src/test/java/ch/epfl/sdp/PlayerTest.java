package ch.epfl.sdp;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ch.epfl.sdp.entity.EnemyOutDated;
import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
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
    private Player player1; //player position is in Geneva
    private EnemyOutDated enemyOutDated1; //enemy1's position is at EPFL
    private EnemyOutDated enemyOutDated2; //enemy2's position is close to player1
    private ArrayList<EnemyOutDated> enemyOutDatedArrayList;
   /* private GeoPoint A;
    private Healthpack healthpack;
    private Shield shield;
    private Shrinker shrinker;
    private Scan scan;*/

    @Before
    public void setup(){
        Game game = new Game();
        PlayerManager playerManager = new PlayerManager();
        player1 = new Player(6.149290, 46.212470, 50, "Skyris", "test@email.com");
        PlayerManager.setUser(player1);
        enemyOutDated1 = new EnemyOutDated(6.568390, 46.520730, 50);
        enemyOutDated2 = new EnemyOutDated(6.149596,46.212437, 50);
        enemyOutDatedArrayList = new ArrayList<EnemyOutDated>();
        /*A = new GeoPoint(6.14308, 46.21023);
        healthpack = new Healthpack(A, false, 25);
        shield = new Shield(A, true, 4, player1);
        shrinker = new Shrinker(A, false, 4, 10,player1);
        scan = new Scan(A, false, 50, new MockMapApi());*/
    }

    @Test
    public void updateHealthTest() {
        // Deprecated
        enemyOutDatedArrayList.add(enemyOutDated1);
        player1.updateHealth(enemyOutDatedArrayList);
        assertEquals(100, player1.getHealthPoints(), 0);
        enemyOutDatedArrayList.add(enemyOutDated2);
        player1.updateHealth(enemyOutDatedArrayList);
        /*assertFalse(player1.getHealthPoints() >= 100);*/
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
        Healthpack healthpack = new Healthpack(1);

        PlayerManager.getUser().setHealthPoints(10);
        healthpack.use();

        assertTrue(PlayerManager.getUser().getHealthPoints() == 11);
    }

    @Test
    public void getEntityTypeReturnsUser() {
        Displayable currentPlayer = new Player(0,0,0,"temp", "fake");
        assertEquals(EntityType.USER, currentPlayer.getEntityType());
    }
}
