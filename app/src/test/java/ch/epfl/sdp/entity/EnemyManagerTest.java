/*package ch.epfl.sdp.entity;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class EnemyManagerTest {

    @Test
    public void updateEnemiesShouldAddEnemy() {
        EnemyManager manager = EnemyManager.getInstance();
        Enemy enemy = new Enemy(1, null, null);
        manager.updateEnemies(enemy);
        assertEquals(1, manager.getEnemies().size());
    }

    @Test
    public void updateEnemiesShouldUpdateEnemyOnly() {
        EnemyManager manager = EnemyManager.getInstance();
        Enemy enemy = new Enemy(1, null, null);
        manager.updateEnemies(enemy);
        manager.updateEnemies(enemy);
        assertEquals(1, manager.getEnemies().size());
    }

    @Test
    public void removeEnemyShouldRemoveEnemy() {
        EnemyManager manager = EnemyManager.getInstance();
        Enemy enemy = new Enemy(1, null, null);
        manager.updateEnemies(enemy);
        manager.removeEnemy(enemy);
        assertEquals(0, manager.getEnemies().size());
    }
}*/
