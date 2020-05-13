package ch.epfl.sdp.artificial_intelligence;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Entity;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.GeoPoint;

public class RandomEnemyGenerator extends EnemyGenerator {

    public RandomEnemyGenerator(Area localArea, Area enclosure) {
        super(localArea, enclosure);
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
    public void generateEnemy(double radius) {
        if (maxEnemies <= enemies.size() || !readyToCreate) {
            return;
        }
        GeoPoint enemyLocation = rule();
        if (enemyLocation == null) {
            return;
        }

        Enemy enemy = new Enemy(localArea, enclosure);
        enemy.setLocation(enemyLocation);
        SinusoidalMovement movement = new SinusoidalMovement();
        movement.setVelocity(5);
        movement.setAngleStep(0.1);
        movement.setAmplitude(10);
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


    @Override
    GeoPoint rule() {
        Random rd = new Random();
        GeoPoint enemyPos;
        int maxIter = 500;
        do {
            enemyPos = localArea.randomLocation();
            --maxIter;
        } while (maxIter > 0 && checkDistanceFromList(enemyPos, (List<Entity>)(List<?>) PlayerManager.getInstance().getPlayers())
                && checkDistanceFromList(enemyPos, (List<Entity>)(List<?>) EnemyManager.getInstance().getEnemies()));
        return enemyPos;
    }

    private boolean checkDistanceFromList(GeoPoint enemyPos, List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.getLocation().distanceTo(enemyPos) < minDistanceFromPlayers) {
                return false;
            }
        }
        return true;
    }
}
