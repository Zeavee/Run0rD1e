package ch.epfl.sdp.entity;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AoeRadiusMovingEntityTest {
    @Test
    public void testGetAndSet() {
        AoeRadiusMovingEntity aoeRadiusMovingEntity = new AoeRadiusMovingEntity();

        aoeRadiusMovingEntity.setAoeRadius(22);
        assertEquals(22, aoeRadiusMovingEntity.getAoeRadius(), 0.01);

        assertNull(aoeRadiusMovingEntity.getEntityType());
        assertEquals(false, aoeRadiusMovingEntity.isOnce());
    }

}
