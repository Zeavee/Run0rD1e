package ch.epfl.sdp.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AoeRadiusMovingEntityTest {
    @Test
    public void testGetAndSet() {
        AoeRadiusEntity aoeRadiusMovingEntity = new Enemy();

        aoeRadiusMovingEntity.setAoeRadius(22);
        assertEquals(22, aoeRadiusMovingEntity.getAoeRadius(), 0.01);
    }

}
