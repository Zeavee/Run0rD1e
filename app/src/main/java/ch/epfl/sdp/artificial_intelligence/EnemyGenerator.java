package ch.epfl.sdp.artificial_intelligence;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.GeoPoint;

public abstract class EnemyGenerator {
    protected double minDistanceFromEnemies;
    protected double minDistanceFromPlayers;
    protected Area enclosure;
    protected int maxEnemies;

    public EnemyGenerator(Area enclosure) {
        this.enclosure = enclosure;
    }

    public abstract void setMaxEnemies(int maxEnemies);

    public abstract void setMinDistanceFromPlayers(double minDistanceFromPlayers);

    public abstract Enemy generateEnemy(double radius);

    public abstract void setMinDistanceFromEnemies(double minDistanceFromEnemies);

    abstract GeoPoint rule();
}
