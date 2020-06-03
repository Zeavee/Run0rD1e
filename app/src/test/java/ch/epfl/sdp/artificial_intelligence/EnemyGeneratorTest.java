package ch.epfl.sdp.artificial_intelligence;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.CircleArea;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.MockMap;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

public class EnemyGeneratorTest {

    @Before
    public void setup() {
        Game.getInstance().setMapApi(new MockMap());
    }

    @Test
    public void testGenerateEnemyCreatesAnEnemy() {
        Player player = new Player("test", "test@gmail.com");
        player.setLocation(new GeoPoint(10, 20));
        PlayerManager.getInstance().setCurrentUser(player);
        EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new CircleArea(5000, player.getLocation()));
        enemyGenerator.setMinDistanceFromEnemies(1);
        enemyGenerator.setMinDistanceFromEnemies(-1);
        enemyGenerator.setEnemyCreationTime(1);
        enemyGenerator.setEnemyCreationTime(-1);
        enemyGenerator.setMaxEnemies(10);
        enemyGenerator.setMaxEnemies(-10);
        enemyGenerator.generateEnemy();
        assertEquals(1, enemyGenerator.getEnemies().size());
        assertNotNull(enemyGenerator.getEnemies().get(0));
    }

    @Test
    public void testSetMinDistanceMakesSpawningFurtherThanMinDistance() {
        Player player = new Player(45, 45, 100, "a", "b");
        PlayerManager.getInstance().addPlayer(player);
        EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new CircleArea(5000, player.getLocation()));
        enemyGenerator.setMinDistanceFromEnemies(10);
        enemyGenerator.setMinDistanceFromPlayers(1000);
        enemyGenerator.setMaxEnemies(10);
        enemyGenerator.setEnemyCreationTime(1);
        while (enemyGenerator.getEnemies().size() < 10) {
            enemyGenerator.generateEnemy();
        }
        for (Enemy e : enemyGenerator.getEnemies()) {
            assertTrue(e.getLocation().distanceTo(player.getLocation()) > 1000);
        }
    }

}