package ch.epfl.sdp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.game.Game;

/**
 * Manages all enemies of a game.
 */
public class EnemyManager {
    private Map<Integer, Enemy> enemies;
    private static final EnemyManager instance = new EnemyManager();

    private EnemyManager() {
        this.enemies = new HashMap<>();
    }

    public static EnemyManager getInstance() {
        return instance;
    }

    public void updateEnemies(Enemy enemy) {
        int enemyId = enemy.getId();
        if (enemies.containsKey(enemyId)) {
            // update the location
            Enemy enemyToBeUpdate = enemies.get(enemyId);
            enemyToBeUpdate.setLocation(enemy.getLocation());
            enemies.put(enemyId, enemyToBeUpdate);
        } else {
            // add a new enemy instance
            enemies.put(enemyId, enemy);
            Game.getInstance().addToDisplayList(enemy);
            Game.getInstance().addToUpdateList(enemy);

        }
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy.getId());
    }

    public List<Enemy> getEnemies() {
        return new ArrayList<>(enemies.values());
    }

    public void removeAll() {
        enemies.clear();
    }
}
