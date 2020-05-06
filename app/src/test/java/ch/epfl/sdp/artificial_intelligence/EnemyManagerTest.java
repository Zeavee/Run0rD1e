package ch.epfl.sdp.artificial_intelligence;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import ch.epfl.sdp.database.firebase.api.ServerMockDatabaseAPI;

import static org.junit.Assert.assertEquals;

public class EnemyManagerTest {
    private EnemyManager enemyManager;

    @Before
    public void setUp() {
        enemyManager = new EnemyManager(new ServerMockDatabaseAPI(new HashMap<>(), new ArrayList<>()));
    }

    @Test
    public void testAddAndRemoveEnemy(){
        Enemy enemy = new Enemy();
        enemyManager.addEnemy(enemy);
        assertEquals(1, enemyManager.getEnemies().size());
        enemyManager.removeEnemy(enemy);
        assertEquals(0, enemyManager.getEnemies().size());
    }

    @Test
    public void testUpdate(){
        Enemy enemy1 = new Enemy();
        enemyManager.addEnemy(enemy1);
        enemyManager.update();
    }
}
