package ch.epfl.sdp.artificial_intelligence;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.geometry.Area;

public abstract class EnemyGenerator {
    protected double minDistanceFromEnemies;
    protected long timeToCreate;
    protected double minDistanceFromPlayers;
    protected final Area enclosure;
    protected List<Enemy> enemies;
    protected Timer timer;
    protected int maxEnemies;
    protected boolean readyToCreate;

    public EnemyGenerator(Area enclosure) {
        this.enclosure = enclosure;
    }

    public abstract void setMaxEnemies(int maxEnemies);

    public abstract void setMinDistanceFromPlayers(double minDistanceFromPlayers);

    public abstract void setEnemyCreationTime(long time);

    public abstract void generateEnemy();

    public abstract void setMinDistanceFromEnemies(double minDistanceFromEnemies);

    public List<Enemy> getEnemies() {
        List<Enemy> clone = new ArrayList<>(enemies.size());
        clone.addAll(enemies);
        return clone;
    }

}
