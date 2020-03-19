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

    public RandomEnemyGenerator(RectangleBounds enclosure) {
        super(enclosure);
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
    public void setMaxEnemiesPerUnitArea(int enemyCount) {

    }


    @Override
    GeoPoint rule() {
        Random rd = new Random();
        long tileIdx = Math.round((rd.nextDouble())*enclosure.getHeight()*enclosure.getWidth()) - 1;
        while(true)
        {
            if (!mapEnemiesToTiles.containsKey(tileIdx))
            {
                mapEnemiesToTiles.put(tileIdx, 1);
            }
        }
        long xcoord = tileIdx % ((int)enclosure.getWidth());
        return null;
    }
}
