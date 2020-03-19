package ch.epfl.sdp.artificial_intelligence;

import ch.epfl.sdp.GeoPoint;

public abstract class EnemyGenerator {

    protected int maxEnemiesPerUnitArea;
    protected int timeToCreate;
    protected double minDistanceFromPlayer;


    abstract void setMinDistanceFromPlayer();
    abstract void generateEnemy(GenPoint point);
    abstract void setEnemyCreationTime(float time);
    abstract void setMaxEnemiesPerUnitArea(int enemyCount);
    abstract void getEnemyIntersectionWithPlayer();

}
