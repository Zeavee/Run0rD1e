package ch.epfl.sdp.artificial_intelligence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.LocalArea;
import ch.epfl.sdp.geometry.PointConverter;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.utils.RandomGenerator;

public class RandomEnemyGenerator extends EnemyGenerator {

    // This area will be split into tiles of size 1x1 in order to fill them according to the density of enemies rule
    private HashMap<Long, Integer> mapEnemiesToTiles;

    public RandomEnemyGenerator(RectangleArea enclosure, Player player) {
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
        GeoPoint enemyPos = RandomGenerator.randomLocationOnCircle(MapsActivity.mapApi.getCurrentLocation(), 100 + rd.nextInt(50000));
        int maxIter = 500;
        while(maxIter > 0) {
            // TODO better randomization
            GeoPoint local = RandomGenerator.randomLocationOnCircle(MapsActivity.mapApi.getCurrentLocation(), 100 + rd.nextInt(50000));
            enemyPos = RandomGenerator.randomLocationOnCircle(MapsActivity.mapApi.getCurrentLocation(), 100 + rd.nextInt(50000));
            Float f1 = rd.nextFloat() * 5000;
            Float f2 = rd.nextFloat() * 5000;
            LocalArea localArea = new LocalArea(new RectangleArea(f1, f2), PointConverter.geoPointToCartesianPoint(local));
            Area area = new UnboundedArea();
            Enemy enemy = new Enemy(maxIter, localArea, area);
            enemy.setLocation(enemyPos);
            SinusoidalMovement movement = new SinusoidalMovement(PointConverter.geoPointToCartesianPoint(enemyPos));
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
