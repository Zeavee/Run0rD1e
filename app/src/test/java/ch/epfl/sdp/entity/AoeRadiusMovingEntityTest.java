package ch.epfl.sdp.entity;

import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.Enemy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AoeRadiusMovingEntityTest {
    @Test
    public void testGetAndSet() {
        AoeRadiusMovingEntity aoeRadiusMovingEntity = new Enemy();

        aoeRadiusMovingEntity.setAoeRadius(22);
        assertEquals(22, aoeRadiusMovingEntity.getAoeRadius(), 0.01);

        assertEquals(aoeRadiusMovingEntity.getEntityType(), EntityType.ENEMY);
        assertEquals(false, aoeRadiusMovingEntity.isOnce());
    }

}
