package ch.epfl.sdp.artificial_intelligence;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entities.artificial_intelligence.Behaviour;
import ch.epfl.sdp.entities.enemy.Enemy;
import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.map.MockMap;

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertTrue;

public class BehaviourTest {
    private Player player;
    private Enemy enemy;

    @Before
    public void setup() {
        Game.getInstance().setMapApi(new MockMap());
        PlayerManager.getInstance().clear();
        GeoPoint local = new GeoPoint(40, 50);
        player = new Player(local.getLongitude(), local.getLatitude(), 0, "", "");
        PlayerManager.getInstance().addPlayer(player);
        PlayerManager.getInstance().setIsServer(true);
        GeoPoint patrolCenter = new GeoPoint(10, 10);
        RectangleArea patrolBounds = new RectangleArea(10, 10, patrolCenter);
        enemy = new Enemy(0, 10, 1, 50, 20, patrolBounds);
        enemy.getMovement().setVelocity(1);
        enemy.setLocation(local);
    }

    @Test
    public void testAttackLessenThePlayerLifeWhenInAttackRange() {
        double health = player.status.getHealthPoints();

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

           assertTrue(health != player.status.getHealthPoints());
        }
    }

    @Test
    public void testAttackChangesToWaitWhenWaiting() {
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
    public void testChaseChangesToWaitWhenWaiting() {
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
    public void testPatrolChangesToWaitWhenWaiting() {
        while (enemy.getBehaviour() == Behaviour.WAIT ||
                enemy.getBehaviour() == Behaviour.WANDER) {
            enemy.update();
        }

        enemy.setWaiting(true);
        enemy.update();

        assertSame(enemy.getBehaviour(), Behaviour.WAIT);
    }

    @Test
    public void testWanderChangesToWaitWhenWaiting() {
        while (enemy.getBehaviour() == Behaviour.WAIT) {
            enemy.update();
        }

        enemy.setWaiting(true);
        enemy.update();

        assertSame(enemy.getBehaviour(), Behaviour.WAIT);
    }
}
