package ch.epfl.sdp.entities;

import org.junit.Test;

import ch.epfl.sdp.entities.enemy.Enemy;

import static org.junit.Assert.assertEquals;

public class AoeRadiusMovingEntityTest {
    @Test
    public void testGetAndSet() {
        AoeRadiusEntity aoeRadiusMovingEntity = new Enemy();

        aoeRadiusMovingEntity.setAoeRadius(22);
        assertEquals(22, aoeRadiusMovingEntity.getAoeRadius(), 0.01);
    }

}
