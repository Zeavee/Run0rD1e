package ch.epfl.sdp.artificial_intelligence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.HashMap;

import ch.epfl.sdp.database.firebase.api.ServerMockDatabaseAPI;
import ch.epfl.sdp.utils.DependencyFactory;

public class EnemyManagerTest {
    @Before
    public void setUp(){
        DependencyFactory.setTestMode(true);
        DependencyFactory.setServerDatabaseAPI(new ServerMockDatabaseAPI(new HashMap<>(), new ArrayList<>()));
    }

    @Test
    public void testAddAndRemoveEnemy(){
        EnemyManager enemyManager = new EnemyManager();
        Enemy enemy = new Enemy();
        enemyManager.addEnemy(enemy);
        assertEquals(1, enemyManager.getEnemies().size());
        enemyManager.removeEnemy(enemy);
        assertEquals(0, enemyManager.getEnemies().size());
    }

    @Test
    public void testUpdate(){
        EnemyManager enemyManager = new EnemyManager();
        Enemy enemy1 = new Enemy();
        enemyManager.addEnemy(enemy1);
        enemyManager.update();
    }

    @After
    public void after() {
        DependencyFactory.setTestMode(false);
        DependencyFactory.setClientDatabaseAPI(null);
        DependencyFactory.setServerDatabaseAPI(null);
    }
}