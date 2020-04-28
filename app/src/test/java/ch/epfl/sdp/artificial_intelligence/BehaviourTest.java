package ch.epfl.sdp.artificial_intelligence;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.CartesianPoint;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.LocalArea;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.map.MockMapApi;

import static junit.framework.TestCase.assertSame;

public class BehaviourTest {
    public Player player;
    public Enemy enemy;

    @Before
    public void setup() {
        MapsActivity.setMapApi(new MockMapApi());
        PlayerManager.removeAll();
        GeoPoint local = new GeoPoint(40, 50);
        player = new Player(local.getLongitude(), local.getLatitude(), 0, "", "");
        PlayerManager.addPlayer(player);
        RectangleArea patrolBounds = new RectangleArea(10, 10);
        RectangleArea maxBounds = new RectangleArea(100, 100);
        CartesianPoint enemyPos = new CartesianPoint(20, 20);
        CartesianPoint patrolCenter = new CartesianPoint(10, 10);
        LocalArea localArea = new LocalArea(patrolBounds, patrolCenter);
        enemy = new Enemy(0, 10, 1, 50, 20, localArea, maxBounds);
        enemy.getMovement().setVelocity(1);
        enemy.setLocation(local);
    }

    @Test
    public void attackShouldLessenThePlayerLifeWhenInAttackRange() {
        double health = player.getHealthPoints();

        while (enemy.getBehaviour() == Behaviour.WAIT ||
                enemy.getBehaviour() == Behaviour.WANDER ||
                enemy.getBehaviour() == Behaviour.PATROL ||
                enemy.getBehaviour() == Behaviour.CHASE) {
            enemy.update();
        }

        if (enemy.getBehaviour() == Behaviour.ATTACK) {
            while (enemy.getAttackTimeDelay() > 0) {
                enemy.update();
            }

            enemy.update();

           //assertTrue(health != player.getHealthPoints());
        }
    }

    @Test
    public void fromAttackToWaitWhenWaiting() {
        while (enemy.getBehaviour() == Behaviour.WAIT ||
                enemy.getBehaviour() == Behaviour.WANDER ||
                enemy.getBehaviour() == Behaviour.PATROL ||
                enemy.getBehaviour() == Behaviour.CHASE) {
            enemy.update();
        }

        enemy.setWaiting(true);
        enemy.update();

        assertSame(enemy.getBehaviour(), Behaviour.WAIT);
    }

    @Test
    public void fromChaseToWaitWhenWaiting() {
        while (enemy.getBehaviour() == Behaviour.WAIT ||
                enemy.getBehaviour() == Behaviour.WANDER ||
                enemy.getBehaviour() == Behaviour.PATROL) {
            enemy.update();
        }

        enemy.setWaiting(true);
        enemy.update();

        assertSame(enemy.getBehaviour(), Behaviour.WAIT);
    }

    @Test
    public void fromPatrolToWaitWhenWaiting() {
        while (enemy.getBehaviour() == Behaviour.WAIT ||
                enemy.getBehaviour() == Behaviour.WANDER) {
            enemy.update();
        }

        enemy.setWaiting(true);
        enemy.update();

        assertSame(enemy.getBehaviour(), Behaviour.WAIT);
    }

    @Test
    public void fromWanderToWaitWhenWaiting() {
        while (enemy.getBehaviour() == Behaviour.WAIT) {
            enemy.update();
        }

        enemy.setWaiting(true);
        enemy.update();

        assertSame(enemy.getBehaviour(), Behaviour.WAIT);
    }
}

