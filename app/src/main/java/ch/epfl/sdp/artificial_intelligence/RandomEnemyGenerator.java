package ch.epfl.sdp.artificial_intelligence;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import ch.epfl.sdp.Enemy;
import ch.epfl.sdp.GeoPoint;
import ch.epfl.sdp.Player;

public class RandomEnemyGenerator extends EnemyGenerator {

    // This area will be split into tiles of size 1x1 in order to fill them according to the density of enemies rule
    private HashMap<Long, Integer> mapEnemiesToTiles;
    //private HashMap<Long, Integer> mapEnemiesToTiles;

    public RandomEnemyGenerator(RectangleBounds enclosure, Player player) {
        super(enclosure, player);
        mapEnemiesToTiles = new HashMap<>();
    }

    @Override
    public void setMinDistanceFromPlayer(double minDistanceFromPlayer) {
        if (minDistanceFromPlayer < 0) return;
        this.minDistanceFromPlayer = minDistanceFromPlayer;
    }

    @Override
    public void generateEnemy(double radius) {

    }

    @Override
    public void setEnemyCreationTime(float time) {

    }

    @Override
    public void setMinDistanceFromPlayer(int minDistanceFromPlayer) {

    }

    @Override
    public void setMaxEnemiesPerUnitArea(int enemyCount) {

    }


    @Override
    GeoPoint rule() {
        Random rd = new Random();
        int maxIter = 500;
        long tileIdx;
        while(maxIter > 0)
        {
            tileIdx = Math.round((rd.nextDouble())*enclosure.getHeight()*enclosure.getWidth()) - 1;
            if (!mapEnemiesToTiles.containsKey(tileIdx))
            {
                mapEnemiesToTiles.put(tileIdx, 1);
                break;
            }
            else {
                int prevCount = mapEnemiesToTiles.get(tileIdx);
                if (prevCount < maxEnemiesPerUnitArea)
                {
                    mapEnemiesToTiles.put(tileIdx, prevCount+1);
                    break;
                }
            }
            double xcoord = tileIdx % ((int)enclosure.getWidth());
            double ycoord = tileIdx / ((int)enclosure.getHeight());
            GeoPoint enemy = new GeoPoint(xcoord + enclosure.getLowerLeftAnchor().longitude(), ycoord+enclosure.getLowerLeftAnchor().latitude());
            if (enemy.distanceTo(player.getLocation()) >= minDistanceFromPlayer) return enemy;
            --maxIter;
        }
        return null;
    }
}
