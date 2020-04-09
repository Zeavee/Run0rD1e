package ch.epfl.sdp.artificial_intelligence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;

import ch.epfl.sdp.entity.EnemyOutDated;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.logic.RandomGenerator;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

public class RandomEnemyGenerator extends EnemyGenerator {

    // This area will be split into tiles of size 1x1 in order to fill them according to the density of enemies rule
    private HashMap<Long, Integer> mapEnemiesToTiles;
    //private HashMap<Long, Integer> mapEnemiesToTiles;

    public RandomEnemyGenerator(RectangleBounds enclosure, Player player) {
        super(enclosure, player);
        mapEnemiesToTiles = new HashMap<>();
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
        enemies.add(new EnemyOutDated(enemyLocation.getLongitude(), enemyLocation.getLatitude(), radius));
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
        GeoPoint enemyPos = RandomGenerator.randomLocationOnCircle(MapsActivity.mapApi.getCurrentLocation(), 100 + rd.nextInt(50000));
        int maxIter = 500;
        while(maxIter > 0) {
            // TODO better randomization
            GeoPoint local = RandomGenerator.randomLocationOnCircle(MapsActivity.mapApi.getCurrentLocation(), 100 + rd.nextInt(50000));
            enemyPos = RandomGenerator.randomLocationOnCircle(MapsActivity.mapApi.getCurrentLocation(), 100 + rd.nextInt(50000));
            Float f1 = rd.nextFloat() * 5000;
            Float f2 = rd.nextFloat() * 5000;
            LocalBounds localBounds = new LocalBounds(new RectangleBounds(f1, f2), PointConverter.GeoPointToGenPoint(local));
            Boundable boundable = new UnboundedArea();
            Enemy enemy = new Enemy(localBounds, boundable);
            enemy.setLocation(enemyPos);
            SinusoidalMovement movement = new SinusoidalMovement(PointConverter.GeoPointToGenPoint(enemyPos));
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
