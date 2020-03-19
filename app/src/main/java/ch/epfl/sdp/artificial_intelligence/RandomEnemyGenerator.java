package ch.epfl.sdp.artificial_intelligence;

import ch.epfl.sdp.Enemy;
import ch.epfl.sdp.GeoPoint;
import ch.epfl.sdp.Player;

public class RandomEnemyGenerator extends EnemyGenerator {

    @Override
    public void setMinDistanceFromPlayer(int minDistanceFromPlayer) {
        if (minDistanceFromPlayer < 0) return;
        this.minDistanceFromPlayer = minDistanceFromPlayer;
    }

    @Override
    public void generateEnemy(GeoPoint point, double radius) {

    }

    @Override
    public void setEnemyCreationTime(float time) {

    }

    @Override
    public void setMaxEnemiesPerUnitArea(int enemyCount) {

    }

    @Override
    public void getEnemyIntersectionWithPlayer(Player user) {

    }


    @Override
    GenPoint rule() {
        return null;
    }
}
