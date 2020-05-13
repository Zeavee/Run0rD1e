package ch.epfl.sdp.artificial_intelligence;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.map.MockMap;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EnemyGeneratorTest {

    @Before
    public void setup() {
        Game.getInstance().setMapApi(new MockMap());
    }

    @Test
    public void generateEnemyWorks() {
        PlayerManager.getInstance().setCurrentUser(new Player("test", "test@gmail.com"));
        EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new RectangleArea(10000, 10000));
        enemyGenerator.setMinDistanceBetweenEnemies(1);
        enemyGenerator.setMinDistanceBetweenEnemies(-1);
        enemyGenerator.setEnemyCreationTime(1);
        enemyGenerator.setEnemyCreationTime(-1);
        enemyGenerator.generateEnemy(100);
        assertEquals(1, enemyGenerator.getEnemies().size());
        assertNotNull(enemyGenerator.getEnemies().get(0));
    }

    @Test
    public void setMaxEnemiesWorks() {
        EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new RectangleArea(1, 1));
        enemyGenerator.setMinDistanceBetweenEnemies(2);
        assertEquals(0, enemyGenerator.getEnemies().size());
    }

    @Test
    public void setMinDistanceWorks() {
        Player player = new Player(45, 45, 100, "a", "b");
        EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new RectangleArea(1, 1));//, player);
        enemyGenerator.setMinDistanceBetweenEnemies(10);
        enemyGenerator.setMinDistanceFromPlayers(1000);
        for (Enemy e : enemyGenerator.getEnemies()) {
            assertEquals(true, e.getLocation().distanceTo(player.getLocation()) > 1000);
        }
    }

}