package ch.epfl.sdp.entities;

import org.junit.Test;

import ch.epfl.sdp.entities.enemy.Enemy;

import static org.junit.Assert.assertEquals;

public class AoeRadiusEntityTest {
    @Test
    public void testGetAndSet() {
        AoeRadiusEntity aoeRadiusEntity = new Enemy();

        aoeRadiusEntity.setAoeRadius(22);
        assertEquals(22, aoeRadiusEntity.getAoeRadius(), 0.01);
    }

}
