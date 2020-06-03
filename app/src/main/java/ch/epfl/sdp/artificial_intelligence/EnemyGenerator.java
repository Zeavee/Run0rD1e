package ch.epfl.sdp.artificial_intelligence;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.geometry.Area;

/**
 * This is will help to spawn the enemies in the map.
 */
public abstract class EnemyGenerator {
    protected double minDistanceFromEnemies;
    protected long timeToCreate;
    protected double minDistanceFromPlayers;
    protected final Area enclosure;
    protected List<Enemy> enemies;
    protected Timer timer;
    protected int maxEnemies;
    protected boolean readyToCreate;

    /**
     * Creates an enemy generator, where the enemies appear inside an area.
     * @param enclosure The area where the enemies will appear.
     */
    public EnemyGenerator(Area enclosure) {
        this.enclosure = enclosure;
    }

    /**
     * Set the maximum number of enemies to be generated.
     * @param maxEnemies The maximum number of enemies to be generated.
     */
    public abstract void setMaxEnemies(int maxEnemies);

    /**
     * Set the minimum distance between the players and an enemy before they are spawned.
     * @param minDistanceFromPlayers The minimum distance between the players and an enemy
     *                               before they are spawned.
     */
    public abstract void setMinDistanceFromPlayers(double minDistanceFromPlayers);

    /**
     * Set the time to spawn new enemies.
     * @param time  The time to spawn new enemies.
     */
    public abstract void setEnemyCreationTime(long time);

    /**
     * Create a new enemy and add it to the list of enemies.
     */
    public abstract void generateEnemy();

    /**
     * Set the distance between enemies before they are spawned.
     * @param minDistanceFromEnemies The distance between enemies before they are spawned.
     */
    public abstract void setMinDistanceFromEnemies(double minDistanceFromEnemies);

    /**
     * Get the list of enemies.
     * @return The list of enemies.
     */
    public List<Enemy> getEnemies() {
        List<Enemy> clone = new ArrayList<>(enemies.size());
        clone.addAll(enemies);
        return clone;
    }

}
