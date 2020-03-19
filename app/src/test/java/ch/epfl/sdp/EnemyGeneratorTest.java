package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.EnemyGenerator;

import static junit.framework.TestCase.assertEquals;

public class EnemyGeneratorTest {
    @Test
    public void generateEnemyWorks() {
        EnemyGenerator enemyGenerator = new TempGen();
        enemyGenerator.setMaxEnemiesPerUnitArea(1);
        enemyGenerator.generateEnemy(new GeoPoint(1, 2), 100);
        assertEquals(1, enemyGenerator.getEnemies().size());
        assertEquals(1.0, enemyGenerator.getEnemies().get(0).location.longitude(), 0.01);
        assertEquals(2.0, enemyGenerator.getEnemies().get(0).location.latitude(), 0.01);
        assertEquals(100, enemyGenerator.getEnemies().get(0).aoeRadius, 0.01);
    }

    @Test
    public void setMaxEnemiesWorks() {
        EnemyGenerator enemyGenerator = new TempGen();
        enemyGenerator.setMaxEnemiesPerUnitArea(2);
        for (int i = 0; i<4; ++i) {
            enemyGenerator.generateEnemy(new GeoPoint(1, 2), 100);
        }
        assertEquals(2, enemyGenerator.getEnemies().size());
    }

    @Test
    public void setMinDistanceWorks() {
        EnemyGenerator enemyGenerator = new TempGen();
        enemyGenerator.setMaxEnemiesPerUnitArea(10);
        enemyGenerator.setMinDistanceFromPlayer(1000);
        GeoPoint playerLocation = new GeoPoint(10, 20);
        for (int i = 0; i<10; ++i) {
            enemyGenerator.generateRandom(playerLocation);
        }
        for (Enemy enemy: enemyGenerator.getEnemies()) {
            assertEquals(true, enemy.location.distanceTo(playerLocation) > 1000);
        }
    }
}
