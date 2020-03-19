package ch.epfl.sdp.artificial_intelligence;

import java.util.List;

import ch.epfl.sdp.Enemy;
import ch.epfl.sdp.GeoPoint;

public abstract class EnemyGenerator {

    protected int maxEnemiesPerUnitArea;
    protected int timeToCreate;
    protected double minDistanceFromPlayer;
    protected List<Enemy> enemies;


    public abstract void generateRandom(GeoPoint playerLocation);
    public abstract void setMinDistanceFromPlayer(double distance);
    public abstract void generateEnemy(GeoPoint point, double aoeRadius);
    public abstract void setEnemyCreationTime(float time);
    public abstract void setMaxEnemiesPerUnitArea(int enemyCount);
    public List<Enemy> getEnemies()
    {
        return enemies;
    }

}
