package ch.epfl.sdp.entities.enemy;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entities.enemy.Enemy;
import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.entities.enemy.RandomEnemyGenerator;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.area.CircleArea;
import ch.epfl.sdp.map.location.GeoPoint;
import ch.epfl.sdp.map.MockMap;

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
        RandomEnemyGenerator enemyGenerator = new RandomEnemyGenerator(new CircleArea(5000, player.getLocation()));
        enemyGenerator.setMinDistanceFromEnemies(1);
        enemyGenerator.setMinDistanceFromEnemies(-1);
        enemyGenerator.setMaxEnemies(10);
        enemyGenerator.setMaxEnemies(-10);
        Enemy enemy = enemyGenerator.generateEnemy();
        assertNotNull(enemy);
    }

    @Test
    public void testSetMinDistanceMakesSpawningFurtherThanMinDistance() {
        Player player = new Player(45, 45, 100, "a", "b");
        PlayerManager.getInstance().addPlayer(player);
        RandomEnemyGenerator enemyGenerator = new RandomEnemyGenerator(new CircleArea(5000, player.getLocation()));
        enemyGenerator.setMinDistanceFromEnemies(10);
        enemyGenerator.setMinDistanceFromPlayers(1000);
        enemyGenerator.setMaxEnemies(10);
        Enemy enemy = enemyGenerator.generateEnemy();
        assertTrue(enemy.getLocation().distanceTo(player.getLocation()) > 1000);
    }

}