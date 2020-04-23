package ch.epfl.sdp.artificial_intelligence;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.entity.Player;

public abstract class EnemyGenerator {

    protected int maxEnemiesPerUnitArea;
    protected float timeToCreate;
    protected double minDistanceFromPlayer;
    protected List<Enemy> enemies;
    protected RectangleBounds enclosure;
    protected Player player;
    protected Timer timer;
    protected float timeRemaining;

    public EnemyGenerator(RectangleBounds enclosure, Player player)
    {
        this.enclosure = enclosure;
        this.player = player;
    }

    public abstract void setMinDistanceFromPlayer(double minDistanceFromPlayer);
    public abstract void setEnemyCreationTime(float time);
    public abstract void generateEnemy(double radius);
    public abstract void setMaxEnemiesPerUnitArea(int enemyCount);
    //public abstract void getEnemyIntersectionWithPlayer(Player user);
    abstract GeoPoint rule();

    public List<Enemy> getEnemies()
    {
        List<Enemy> clone = new ArrayList<>(enemies.size());
        for (Enemy item : enemies) clone.add(item);
        return clone;
    }

}
