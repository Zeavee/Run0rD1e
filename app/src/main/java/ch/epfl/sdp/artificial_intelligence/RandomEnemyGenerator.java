package ch.epfl.sdp.artificial_intelligence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.entity.Player;

public class RandomEnemyGenerator extends EnemyGenerator {

    // This area will be split into tiles of size 1x1 in order to fill them according to the density of enemies rule
    private HashMap<Long, Integer> mapEnemiesToTiles;
    //private HashMap<Long, Integer> mapEnemiesToTiles;

    public RandomEnemyGenerator(RectangleBounds enclosure, Player player) {
        super(enclosure, player);
        mapEnemiesToTiles = new HashMap<>();
        enemies = new ArrayList<Enemy>();
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
        //enemies.add(new EnemyOutDated(enemyLocation.getLongitude(), enemyLocation.getLatitude(), radius));
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
        Thread thread = new Thread();
        thread.run();
        int maxIter = 500;
        while(maxIter > 0)
        {
            double xcoord = rd.nextDouble() * enclosure.getWidth();
            double ycoord = rd.nextDouble() * enclosure.getHeight();
            GeoPoint enemy = new GeoPoint(xcoord + enclosure.getMidPoint().getLongitude(), ycoord+enclosure.getMidPoint().getLatitude());
            double distance = enemy.distanceTo(player.getLocation());
            if (distance > minDistanceFromPlayer) return enemy;
            --maxIter;
        }
        return null;
    }
}
