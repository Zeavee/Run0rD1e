package ch.epfl.sdp.game;

import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.artificial_intelligence.EnemyGenerator;
import ch.epfl.sdp.artificial_intelligence.EnemyManager;

/**
 * Takes care of all actions that a server should perform (generating enemies, updating enemies etc.).
 */
public class Server implements Updatable {
    public final int GENERATE_ENEMY_EVERY_MS = 10000;

    private long lastEnemyGenerateTimeMillis = System.currentTimeMillis();

    private EnemyManager manager;
    private EnemyGenerator enemyGenerator;

    public Server(EnemyManager manager, EnemyGenerator enemyGenerator) {
        this.manager = manager;
        this.enemyGenerator = enemyGenerator;
    }

    @Override
    public void update() {
        long currentTimeMillis = System.currentTimeMillis();
        if(currentTimeMillis - lastEnemyGenerateTimeMillis >= GENERATE_ENEMY_EVERY_MS) {
            //Enemy enemy = enemyGenerator.generateEnemy(100);
            Enemy enemy = new Enemy();
            lastEnemyGenerateTimeMillis = currentTimeMillis;
            manager.addEnemy(enemy);
        }

        manager.update();
    }
}
