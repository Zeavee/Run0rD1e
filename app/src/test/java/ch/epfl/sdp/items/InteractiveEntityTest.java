package ch.epfl.sdp.items;

import org.junit.Test;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.InteractiveEntity;
import ch.epfl.sdp.map.MapApi;

import static org.junit.Assert.assertEquals;

public class InteractiveEntityTest  {
    @Test
    public void testGetAndSet() {
        InteractiveEntity interactiveEntity1 = new InteractiveEntity(EntityType.ITEMBOX, new GeoPoint(22, 22)) {
            @Override
            public void displayOn(MapApi mapApi) {

            }

            @Override
            public boolean isOnce() {
                return false;
            }
        };

        interactiveEntity1.setActive(true);
        assertEquals(true, interactiveEntity1.isActive());

        interactiveEntity1.setLocation(new GeoPoint(22,22));
        assertEquals(22, interactiveEntity1.getLocation().getLatitude(), 0.01);
    }
}
