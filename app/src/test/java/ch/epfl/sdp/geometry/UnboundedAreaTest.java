package ch.epfl.sdp.geometry;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.geometry.area.UnboundedArea;
import ch.epfl.sdp.map.MockMap;
import ch.epfl.sdp.map.location.GeoPoint;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class UnboundedAreaTest {
    private UnboundedArea unboundedArea;
    private MockMap mapApi;

    @Before
    public void setUp() {
        unboundedArea = new UnboundedArea();
        mapApi = new MockMap();
    }

    @Test
    public void checkLocationsWork() {
        assertNotNull(unboundedArea.getLocation());
        GeoPoint randomLoc = unboundedArea.randomLocation();
        assertNotNull(randomLoc);
        assertTrue(unboundedArea.isInside(randomLoc));
    }

    @Test
    public void displayDoesNotDoAnything() {
        unboundedArea.displayOn(mapApi);
        assertTrue(mapApi.getDisplayables().isEmpty());
    }

    @Test
    public void updateAreaDoesNotDoAnything() {
        GeoPoint center = unboundedArea.getLocation();
        unboundedArea.updateGameArea(new UnboundedArea());
        assertTrue(center.distanceTo(unboundedArea.getLocation()) < 0.01);
    }

    @Test
    public void testShrink() {
        //We want to check that shrink does not do anything
        GeoPoint center = unboundedArea.getLocation();
        unboundedArea.shrink(0.5);
        assertEquals(center, unboundedArea.getLocation());
        unboundedArea.setFinalTime(2);
        unboundedArea.setTime(1);
        unboundedArea.setShrinkTransition();
        assertEquals(center, unboundedArea.getLocation());
        unboundedArea.finishShrink();
        assertEquals(center, unboundedArea.getLocation());
    }
}
