package ch.epfl.sdp;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.EnemyGenerator;
import ch.epfl.sdp.artificial_intelligence.RandomEnemyGenerator;
import ch.epfl.sdp.artificial_intelligence.RectangleBounds;
import ch.epfl.sdp.entity.EnemyOutDated;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EnemyOutDatedGeneratorTest {

    @Before
    public void setup(){
        MapsActivity.setMapApi(new MockMapApi());
    }

    @Test
    public void generateEnemyWorks() {
       /* EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new RectangleBounds(10000, 10000),
                new Player(45, 45, 100, "a", "b"));
        enemyGenerator.setMaxEnemiesPerUnitArea(1);
        enemyGenerator.setMaxEnemiesPerUnitArea(-1);
        enemyGenerator.setEnemyCreationTime(1);
        enemyGenerator.setEnemyCreationTime(-1);
        enemyGenerator.generateEnemy(100);
        assertEquals(1, enemyGenerator.getEnemies().size());
        assertNotNull(enemyGenerator.getEnemies().get(0));*/
    }

    @Test
    public void setMaxEnemiesWorks() {
      /*  EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new RectangleBounds(1, 1),
                new Player(45, 45, 100, "a", "b"));
        enemyGenerator.setMaxEnemiesPerUnitArea(2);
        for (int i = 0; i<4; ++i) {
            enemyGenerator.generateEnemy(100);
        }
        assertEquals(2, enemyGenerator.getEnemies().size()); */
    }

    @Test
    public void setMinDistanceWorks() {
        Player player = new Player(45, 45, 100, "a", "b");
        EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new RectangleBounds(1, 1), player);
        enemyGenerator.setMaxEnemiesPerUnitArea(10);
        enemyGenerator.setMinDistanceFromPlayer(1000);
        for (int i = 0; i<10; ++i) {
            enemyGenerator.generateEnemy(100);
        }
        for (EnemyOutDated enemyOutDated : enemyGenerator.getEnemies()) {
            assertEquals(true, enemyOutDated.getLocation().distanceTo(player.getLocation()) > 1000);
        }
    }

    @Test
    public void ifMaxIterNothingAdded() {
        EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new RectangleBounds(1, 1),
                new Player(MapsActivity.mapApi.getCurrentLocation().getLongitude(),MapsActivity.mapApi.getCurrentLocation().getLatitude(), 100, "a", "b"));
        enemyGenerator.setMaxEnemiesPerUnitArea(5);
        enemyGenerator.setMinDistanceFromPlayer(1000000);
        enemyGenerator.generateEnemy(100);
        assertEquals(0, enemyGenerator.getEnemies().size());
    }
}
