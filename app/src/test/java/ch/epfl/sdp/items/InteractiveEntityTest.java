package ch.epfl.sdp.items;

import org.junit.Test;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.InteractiveEntity;

import static org.junit.Assert.assertEquals;

public class InteractiveEntityTest  {
    @Test
    public void testGetAndSet() {
        InteractiveEntity interactiveEntity1 = new InteractiveEntity(EntityType.ENEMY, new GeoPoint(22, 22)) {
            @Override
            public boolean isOnce() {
                return false;
            }
        };

        interactiveEntity1.setActive(true);
        assertEquals(true, interactiveEntity1.isActive());

        interactiveEntity1.setLocation(new GeoPoint(22,22));
        assertEquals(22, interactiveEntity1.getLocation().getLatitude(), 0.01);

        assertEquals(EntityType.ENEMY, interactiveEntity1.getEntityType());
    }
}