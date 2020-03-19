package ch.epfl.sdp.artificial_intelligence;

import java.util.List;

import ch.epfl.sdp.Enemy;
import ch.epfl.sdp.GeoPoint;

public abstract class EnemyGenerator {

    protected int maxEnemiesPerUnitArea;
    protected int timeToCreate;
    protected double minDistanceFromPlayer;
    protected List<Enemy> enemies;


    abstract void setMinDistanceFromPlayer();
    abstract void generateEnemy(GenPoint point);
    abstract void setEnemyCreationTime(float time);
    abstract void setMaxEnemiesPerUnitArea(int enemyCount);
    abstract void getEnemyIntersectionWithPlayer();
    List<Enemy> getEnemies()
    {
        return enemies;
    }

}
