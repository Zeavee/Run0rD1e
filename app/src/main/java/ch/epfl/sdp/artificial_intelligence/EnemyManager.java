package ch.epfl.sdp.artificial_intelligence;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.database.ServerDatabaseAPI;

/**
 * Manages all enemies of a game.
 */
public class EnemyManager implements Updatable {
    public final int UPDATE_EVERY_MS = 1000;

    private ServerDatabaseAPI serverDatabaseAPI;
    private List<Enemy> enemies = new ArrayList<>();
    private long lastUpdateTimeMillis = System.currentTimeMillis();

    public EnemyManager(ServerDatabaseAPI serverDatabaseAPI) {
        this.serverDatabaseAPI = serverDatabaseAPI;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    @Override
    public void update() {
        long currentTimeMillis = System.currentTimeMillis();
        if(lastUpdateTimeMillis - currentTimeMillis >= UPDATE_EVERY_MS) {
            serverDatabaseAPI.sendEnemies(enemies);
            lastUpdateTimeMillis = currentTimeMillis;
        }
    }
}
