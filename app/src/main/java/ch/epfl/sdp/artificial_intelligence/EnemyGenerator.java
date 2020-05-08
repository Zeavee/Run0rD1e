package ch.epfl.sdp.artificial_intelligence;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.RectangleArea;

public abstract class EnemyGenerator {
    protected int maxEnemiesPerUnitArea;
    protected float timeToCreate;
    protected double minDistanceFromPlayer;
    protected RectangleArea enclosure;
    protected List<Enemy> enemies;
    protected Timer timer;
    protected float timeRemaining;

    public EnemyGenerator(RectangleArea enclosure) {
        this.enclosure = enclosure;
    }

    public abstract void setMinDistanceFromPlayer(double minDistanceFromPlayer);

    public abstract void setEnemyCreationTime(float time);

    public abstract void generateEnemy(double radius);

    public abstract void setMaxEnemiesPerUnitArea(int enemyCount);

    abstract GeoPoint rule();

    public List<Enemy> getEnemies() {
        List<Enemy> clone = new ArrayList<>(enemies.size());
        for (Enemy item : enemies) clone.add(item);
        return clone;
    }

}
