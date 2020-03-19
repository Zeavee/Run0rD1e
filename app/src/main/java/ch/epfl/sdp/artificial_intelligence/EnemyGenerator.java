package ch.epfl.sdp.artificial_intelligence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sdp.Enemy;
import ch.epfl.sdp.GeoPoint;
import ch.epfl.sdp.Player;

public abstract class EnemyGenerator {

    protected int maxEnemiesPerUnitArea;
    protected int timeToCreate;
    protected double minDistanceFromPlayer;
    protected List<Enemy> enemies;


    public abstract void setMinDistanceFromPlayer(int minDistanceFromPlayer);
    public abstract void generateEnemy(double radius);
    public abstract void setEnemyCreationTime(float time);
    public abstract void setMaxEnemiesPerUnitArea(int enemyCount);
    public abstract void getEnemyIntersectionWithPlayer(Player user);
    abstract GeoPoint rule();
    public List<Enemy> getEnemies()
    {
        List<Enemy> clone = new ArrayList<Enemy>(enemies.size());
        for (Enemy item : clone) clone.add(item);
        return clone;
    }

}
