package ch.epfl.sdp.artificial_intelligence;

import java.util.List;
import java.util.Random;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Entity;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.GeoPoint;


/**
 * Class that randomly generates an enemy
 * This class differs from RandomGenerator, as it specifically generates an enemy (and nothing else)
 */
public class RandomEnemyGenerator {
    protected double minDistanceFromEnemies;
    protected double minDistanceFromPlayers;
    protected Area enclosure;
    protected int maxEnemies;
    private int generatedEnemyNum;

    public RandomEnemyGenerator(Area enclosure) {
        this.enclosure = enclosure;
        generatedEnemyNum = 0;
    }


    public void setMaxEnemies(int maxEnemies) {
        if (maxEnemies < 0) return;
        this.maxEnemies = maxEnemies;
    }


    public void setMinDistanceFromPlayers(double minDistanceFromPlayers) {
        if (minDistanceFromPlayers < 0) return;
        this.minDistanceFromPlayers = minDistanceFromPlayers;
    }


    public Enemy generateEnemy(double radius) {
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


    public void setMinDistanceFromEnemies(double minDistanceFromEnemies) {
        if (minDistanceFromEnemies < 0) return;
        this.minDistanceFromEnemies = minDistanceFromEnemies;
    }

    GeoPoint rule() {
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