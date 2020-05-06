package ch.epfl.sdp.artificial_intelligence;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.utils.MockMapApi;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EnemyGeneratorTest {

    @Before
    public void setup() {
        Game.getInstance().setMapApi(new MockMapApi());
    }
/*
    @Test
    public void generateEnemyWorks() {
        EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new RectangleArea(10000, 10000));
         //       new Player(45, 45, 100, "a", "b"));
        enemyGenerator.setMaxEnemiesPerUnitArea(1);
        enemyGenerator.setMaxEnemiesPerUnitArea(-1);
        enemyGenerator.setEnemyCreationTime(1);
        enemyGenerator.setEnemyCreationTime(-1);
       // enemyGenerator.generateEnemy(100);
        assertEquals(1, enemyGenerator.getEnemies().size());
        assertNotNull(enemyGenerator.getEnemies().get(0));
    }

    @Test
    public void setMaxEnemiesWorks() {

        EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new RectangleArea(1, 1));
           //     new Player(45, 45, 100, "a", "b"));
        enemyGenerator.setMaxEnemiesPerUnitArea(2);
        for (int i = 0; i < 4; ++i) {
       //     enemyGenerator.generateEnemy(100);
        }
        assertEquals(2, enemyGenerator.getEnemies().size());
    }*/

    @Test
    public void setMinDistanceWorks() {
       // Player player = new Player(45, 45, 100, "a", "b");
        EnemyGenerator enemyGenerator = new RandomEnemyGenerator(new RectangleArea(1, 1));//, player);
        enemyGenerator.setMaxEnemiesPerUnitArea(10);
        enemyGenerator.setMinDistanceFromPlayer(1000);
        for (int i = 0; i < 10; ++i) {
        //    enemyGenerator.generateEnemy(100);
        }
        for (Enemy e : enemyGenerator.getEnemies()) {
           // assertEquals(true, e.getLocation().distanceTo(player.getLocation()) > 1000);
        }
    }

}