package ch.epfl.sdp.artificial_intelligence;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Entity;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.GeoPoint;

public class RandomEnemyGenerator extends EnemyGenerator {

    public RandomEnemyGenerator(Area enclosure) {
        super(enclosure);
        enemies = new ArrayList<>();
        timer = new Timer();
        readyToCreate = true;
    }

    @Override
    public void setMaxEnemies(int maxEnemies) {
        if (maxEnemies < 0) return;
        this.maxEnemies = maxEnemies;
    }

    @Override
    public void setMinDistanceFromPlayers(double minDistanceFromPlayers) {
        if (minDistanceFromPlayers < 0) return;
        this.minDistanceFromPlayers = minDistanceFromPlayers;
    }

    @Override
    public void generateEnemy() {
        if (maxEnemies <= enemies.size() || !readyToCreate) {
            return;
        }
        GeoPoint enemyLocation = rule();
        if (enemyLocation == null) {
            return;
        }

        Enemy enemy = new Enemy(enclosure);
        SinusoidalMovement movement = new SinusoidalMovement();
        movement.setVelocity(10);
        movement.setAngleStep(0.1);
        movement.setAmplitude(1);
        movement.setAngle(1);
        enemy.setMovement(movement);
        enemy.setLocation(enemyLocation);
        enemies.add(enemy);
        readyToCreate = false;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                readyToCreate = true;
            }
        }, timeToCreate);
    }

    @Override
    public void setEnemyCreationTime(long time) {
        if (time < 0) {
            return;
        }
        timeToCreate = time;
    }

    @Override
    public void setMinDistanceFromEnemies(double minDistanceFromEnemies) {
        if (minDistanceFromEnemies < 0) return;
        this.minDistanceFromEnemies = minDistanceFromEnemies;
    }

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