package ch.epfl.sdp.entity;

import org.junit.Before;
import org.junit.Test;

import static ch.epfl.sdp.artificial_intelligence.Behaviour.PATROL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnemyTest {

    @Test
    public void getIdShouldWork() {
        Enemy enemy = new Enemy();
        enemy.setId(22);
        enemy.getId();
        assertEquals(22,enemy.getId());
        assertTrue(enemy.getId() == 22);
        assertFalse(enemy.getId() != 22);
    }

    @Test
    public void setIdShouldWork() {
        Enemy enemy = new Enemy();
        enemy.setId(10);
        assertEquals(10, enemy.getId());
        assertTrue(enemy.getId() == 10);
    }

    @Test
    public void getAttackTimeDelayShouldWork() {
        Enemy enemy = new Enemy();
        enemy.getAttackTimeDelay();
        assertEquals(30,enemy.getAttackTimeDelay());
        assertTrue(enemy.getAttackTimeDelay() == 30);
        assertFalse(enemy.getAttackTimeDelay() != 30);
    }

    @Test
    public void getBehaviourPatrolShouldWork() {
        Enemy enemy = new Enemy();
        enemy.getBehaviour();
        assertEquals(PATROL,enemy.getBehaviour());
    }

    @Test
    public void isWaitingCorrectly() {
        Enemy enemy = new Enemy();
        enemy.checkWaiting();
        assertFalse(enemy.checkWaiting() == true);
        assertTrue(enemy.checkWaiting() == false);
    }
}
