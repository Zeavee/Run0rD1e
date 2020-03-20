package ch.epfl.sdp;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.artificial_intelligence.Behaviour;
import ch.epfl.sdp.artificial_intelligence.CartesianPoint;
import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.artificial_intelligence.LocalBounds;
import ch.epfl.sdp.artificial_intelligence.RectangleBounds;

import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.assertTrue;

public class BehaviourTest {
    public Player player;
    public Enemy enemy;

    @Before
    public void setup() {
        player = new Player(0, 0, 0, "", "");
        List<Player> players = new ArrayList<>();
        players.add(player);
        RectangleBounds patrolBounds = new RectangleBounds(10, 10, null);
        RectangleBounds maxBounds = new RectangleBounds(100, 100, null);
        CartesianPoint enemyPos = new CartesianPoint(20, 20);
        CartesianPoint patrolCenter = new CartesianPoint(10, 10);
        LocalBounds localBounds = new LocalBounds(null, null);
        localBounds.setBounds(patrolBounds);
        localBounds.setPosition(patrolCenter);
        enemy = new Enemy(players, 10, 1, 50, localBounds, maxBounds);
        enemy.setVelocity(1);
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
            while (enemy.getTimeAttack() > 0) {
                enemy.update();
            }

            enemy.update();

            assertTrue(health != player.getHealthPoints());
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