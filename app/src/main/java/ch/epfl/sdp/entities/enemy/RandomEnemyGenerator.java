package ch.epfl.sdp.entities.enemy;

import java.util.List;

import ch.epfl.sdp.entities.Entity;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.entities.enemy.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.geometry.area.Area;
import ch.epfl.sdp.map.location.GeoPoint;


/**
 * Class that randomly generates an enemy
 * This class differs from RandomGenerator, as it specifically generates an enemy (and nothing else)
 */
public class RandomEnemyGenerator {
    private double minDistanceFromEnemies;
    private double minDistanceFromPlayers;
    private final Area enclosure;
    private int maxEnemies;
    private int generatedEnemyNum;

    /**
     * Creates an enemy generator, where the enemies appear inside an area.
     *
     * @param enclosure The area where the enemies will appear.
     */
    public RandomEnemyGenerator(Area enclosure) {
        this.enclosure = enclosure;
        generatedEnemyNum = 0;
    }

    /**
     * Set the maximum number of enemies to be generated.
     *
     * @param maxEnemies The maximum number of enemies to be generated.
     */
    public void setMaxEnemies(int maxEnemies) {
        if (maxEnemies < 0) return;
        this.maxEnemies = maxEnemies;
    }

    /**
     * Set the minimum distance between the players and an enemy before they are spawned.
     *
     * @param minDistanceFromPlayers The minimum distance between the players and an enemy
     *                               before they are spawned.
     */
    public void setMinDistanceFromPlayers(double minDistanceFromPlayers) {
        if (minDistanceFromPlayers < 0) return;
        this.minDistanceFromPlayers = minDistanceFromPlayers;
    }

    /**
     * Create a new enemy
     *
     * @return return the enemy just created
     */
    public Enemy generateEnemy() {
        if (maxEnemies <= generatedEnemyNum) {
            return null;
        }
        GeoPoint enemyLocation = rule();
        if (enemyLocation == null) {
            return null;
        }

        // use the number of the enemies as the enemy id
        Enemy enemy = new Enemy(generatedEnemyNum++, enclosure);
        SinusoidalMovement movement = new SinusoidalMovement();
        movement.setVelocity(10);
        movement.setAngleStep(0.1);
        movement.setAmplitude(1);
        movement.setAngle(1);
        enemy.setMovement(movement);
        enemy.setLocation(enemyLocation);
        return enemy;
    }

    /**
     * Set the distance between enemies before they are spawned.
     *
     * @param minDistanceFromEnemies The distance between enemies before they are spawned.
     */
    public void setMinDistanceFromEnemies(double minDistanceFromEnemies) {
        if (minDistanceFromEnemies < 0) return;
        this.minDistanceFromEnemies = minDistanceFromEnemies;
    }

    /**
     * Randomly generate the enemy's location based on the rule we just set: inside the gameArea,
     * keep the minimum distance both from players and other enemies
     *
     * @return The location just generated
     */
    private GeoPoint rule() {
        GeoPoint enemyPos;
        int maxIteration = 500;
        do {
            enemyPos = enclosure.randomLocation();
            --maxIteration;
        } while (maxIteration > 0 && (checkDistanceFromList(enemyPos, (List<Entity>) (List<?>) PlayerManager.getInstance().getPlayers(), minDistanceFromPlayers)
                || checkDistanceFromList(enemyPos, (List<Entity>) (List<?>) EnemyManager.getInstance().getEnemies(), minDistanceFromEnemies)));
        return enemyPos;
    }

    private boolean checkDistanceFromList(GeoPoint enemyPos, List<Entity> entities, double minDistance) {
        for (Entity entity : entities) {
            if (entity.getLocation().distanceTo(enemyPos) < minDistance) {
                return true;
            }
        }
        return false;
    }
}