package ch.epfl.sdp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ch.epfl.sdp.game.Game;

/**
 * Manages all enemies of a game.
 */
public class EnemyManager {
    private final Map<Integer, Enemy> enemies;
    private static final EnemyManager instance = new EnemyManager();

    private EnemyManager() {
        this.enemies = new HashMap<>();
    }

    /**
     * This method returns the instance of the singleton EnemyManager
     *
     * @return the instance of the singleton EnemyManager
     */
    public static EnemyManager getInstance() {
        return instance;
    }

    /**
     * This method updates the given enemy
     *
     * @param enemy the enemy we want to update
     */
    public void updateEnemies(Enemy enemy) { // TODO update
        int enemyId = enemy.getId();
        if (enemies.containsKey(enemyId)) {
            // update the location
            Enemy enemyToBeUpdate = enemies.get(enemyId);
            Objects.requireNonNull(enemyToBeUpdate).setLocation(enemy.getLocation());
            enemyToBeUpdate.setBehaviour(enemy.getBehaviour());
            enemyToBeUpdate.getMovement().setOrientation(enemy.getMovement().getOrientation());
            enemies.put(enemyId, enemyToBeUpdate);
        } else {
            // add a new enemy instance
            enemies.put(enemyId, enemy);
            Game.getInstance().addToDisplayList(enemy);
            Game.getInstance().addToUpdateList(enemy);
        }
    }

    /**
     * This method removes an enemy from the list
     *
     * @param enemy the enemy we want to remove
     */
    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy.getId());
    }

    /**
     * This method returns the list of all the enemies
     *
     * @return the list of all the enemies
     */
    public List<Enemy> getEnemies() {
        return new ArrayList<>(enemies.values());
    }

    /**
     * This method removes all the enemies of the list
     */
    public void clear() {
        enemies.clear();
    }

    public Boolean enemyExists(Enemy enemy){
        return enemies.containsKey(enemy.getId());
    }
}
