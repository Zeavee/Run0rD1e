package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.EnemyGenerator;
import ch.epfl.sdp.artificial_intelligence.RandomEnemyGenerator;
import ch.epfl.sdp.artificial_intelligence.RectangleBounds;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EnemyGeneratorTest {
    @Test
    public void generateEnemyWorks() {
        EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new RectangleBounds(10000, 10000, new GeoPoint(44.999, 44.999)),
                new Player(45, 45, 100, "a", "b"));
        enemyGenerator.setMaxEnemiesPerUnitArea(1);
        enemyGenerator.setMaxEnemiesPerUnitArea(-1);
        enemyGenerator.setEnemyCreationTime(1);
        enemyGenerator.setEnemyCreationTime(-1);
        enemyGenerator.generateEnemy(100);
        assertEquals(1, enemyGenerator.getEnemies().size());
        assertNotNull(enemyGenerator.getEnemies().get(0));
    }

    @Test
    public void setMaxEnemiesWorks() {
        EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new RectangleBounds(1, 1, new GeoPoint(44.999, 44.999)),
                new Player(45, 45, 100, "a", "b"));
        enemyGenerator.setMaxEnemiesPerUnitArea(2);
        for (int i = 0; i<4; ++i) {
            enemyGenerator.generateEnemy(100);
        }
        assertEquals(2, enemyGenerator.getEnemies().size());
    }

    @Test
    public void setMinDistanceWorks() {
        Player player = new Player(45, 45, 100, "a", "b");
        EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new RectangleBounds(1, 1, new GeoPoint(44.999, 44.999)), player);
        enemyGenerator.setMaxEnemiesPerUnitArea(10);
        enemyGenerator.setMinDistanceFromPlayer(1000);
        for (int i = 0; i<10; ++i) {
            enemyGenerator.generateEnemy(100);
        }
        for (Enemy enemy: enemyGenerator.getEnemies()) {
            assertEquals(true, enemy.location.distanceTo(player.location) > 1000);
        }
    }

    @Test
    public void ifMaxIterNothingAdded() {
        EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new RectangleBounds(1, 1, new GeoPoint(44.999, 44.999)),
                new Player(45, 45, 100, "a", "b"));
        enemyGenerator.setMaxEnemiesPerUnitArea(5);
        enemyGenerator.setMinDistanceFromPlayer(1000000);
        enemyGenerator.generateEnemy(100);
        assertEquals(0, enemyGenerator.getEnemies().size());
    }
}
