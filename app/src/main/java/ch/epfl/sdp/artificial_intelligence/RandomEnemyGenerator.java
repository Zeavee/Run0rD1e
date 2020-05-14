package ch.epfl.sdp.artificial_intelligence;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.LocalArea;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.utils.RandomGenerator;

public class RandomEnemyGenerator extends EnemyGenerator {

    public RandomEnemyGenerator(RectangleArea enclosure) {
        super(enclosure);
        enemies = new ArrayList<>();
        timer = new Timer();
    }

    @Override
    public void setMinDistanceFromPlayer(double minDistanceFromPlayer) {
        if (minDistanceFromPlayer < 0) return;
        this.minDistanceFromPlayer = minDistanceFromPlayer;
    }

    @Override
    public void generateEnemy(double radius) {
        if (maxEnemiesPerUnitArea <= enemies.size()) {
            return;
        }
        GeoPoint enemyLocation = rule();
        if (enemyLocation == null) {
            return;
        }

        Enemy e = new Enemy();
        e.setLocation(enemyLocation);
        enemies.add(e);
    }

    @Override
    public void setEnemyCreationTime(float time) {
        if (time<0) {
            return;
        }
        timeToCreate = time;
    }

    @Override
    public void setMaxEnemiesPerUnitArea(int enemyCount) {
        if (enemyCount<0) {
            return;
        }
        maxEnemiesPerUnitArea = enemyCount;
    }


    @Override
    GeoPoint rule() {
        Random rd = new Random();
        GeoPoint enemyPos = RandomGenerator.randomLocationOnCircle(PlayerManager.getInstance().getCurrentUser().getLocation(), 100 + rd.nextInt(50000));
        int maxIter = 500;
        while(maxIter > 0) {
            // TODO better randomization
            GeoPoint local = RandomGenerator.randomLocationOnCircle(PlayerManager.getInstance().getCurrentUser().getLocation(), 100 + rd.nextInt(50000));
            enemyPos = RandomGenerator.randomLocationOnCircle(PlayerManager.getInstance().getCurrentUser().getLocation(), 100 + rd.nextInt(50000));
            Float f1 = rd.nextFloat() * 5000;
            Float f2 = rd.nextFloat() * 5000;
            LocalArea localArea = new LocalArea(new RectangleArea(f1, f2), local);
            Area area = new UnboundedArea();
            LocalArea localArea1 = new LocalArea(area, local);
            Enemy enemy = new Enemy(maxIter, localArea, localArea);
            enemy.setLocation(enemyPos);
            SinusoidalMovement movement = new SinusoidalMovement();
            movement.setVelocity(5);
            movement.setAngleStep(0.1);
            movement.setAmplitude(10);
            movement.setAngle(1);
            enemy.setMovement(movement);

            --maxIter;
        }
        return enemyPos;
    }
}
