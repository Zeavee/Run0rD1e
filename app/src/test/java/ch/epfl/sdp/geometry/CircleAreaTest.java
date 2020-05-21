package ch.epfl.sdp.geometry;

import junit.framework.TestCase;

import org.junit.Test;

import ch.epfl.sdp.map.MockMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CircleAreaTest {

    @Test
    public void shrinkWithNegativeOrTooBigFactorDoesNotDoAnything() {
        CircleArea circleArea = new CircleArea(100, new GeoPoint(40, 50));
        circleArea.shrink(-1);
        assertEquals(circleArea.getRadius(), 100, 0.01);
        circleArea.shrink(1.5);
        assertEquals(circleArea.getRadius(), 100, 0.01);
        assertFalse(circleArea.isShrinking());
    }

    @Test
    public void shrinkWorks() {
        GeoPoint oldCenter = new GeoPoint(40, 50);
        CircleArea oldCircleArea = new CircleArea(10000, oldCenter);
        oldCircleArea.shrink(0.5);
        assertEquals(oldCircleArea.getNewRadius(), 10000 * 0.5, 0.01);
        assertTrue(oldCenter.distanceTo(oldCircleArea.getNewLocation()) < 10000 * 0.5);
        assertEquals(oldCircleArea.getOldRadius(), 10000, 0.01);
        assertTrue(oldCenter.distanceTo(oldCircleArea.getOldLocation()) < 0.01);
        oldCircleArea.finishShrink();
        assertFalse(oldCircleArea.isShrinking());
        assertEquals(10000 * 0.5, oldCircleArea.getRadius(), 0.01);
        assertTrue(oldCircleArea.getLocation().distanceTo(oldCircleArea.getNewLocation()) < 0.01);
    }

    @Test
    public void randomLocationWorks() {
        GeoPoint center = new GeoPoint(30, 40);
        CircleArea circleArea = new CircleArea(1000, center);
        GeoPoint randomLocation = circleArea.randomLocation();
        assertTrue(center.distanceTo(randomLocation) < circleArea.getRadius());
    }

    @Test
    public void isInsideWorks() {
        GeoPoint center = new GeoPoint(30, 40);
        CircleArea circleArea = new CircleArea(1000, center);
        assertTrue(center.distanceTo(new GeoPoint(30.001, 40)) < 1000);
        assertTrue(circleArea.isInside(new GeoPoint(30.001, 40)));
    }

    @Test
    public void getShrinkTransitionWorks() {
        GeoPoint oldCenter = new GeoPoint(40, 50);
        CircleArea circle = new CircleArea(10000, oldCenter);
        circle.shrink(0.5);
        circle.setTime(0);
        circle.setFinalTime(2);
        circle.setShrinkTransition();
        assertEquals(10000, circle.getRadius(), 0.01);
        assertEquals(0, circle.getLocation().distanceTo(oldCenter), 0.01);

        circle.setTime(1);
        circle.setShrinkTransition();
        assertEquals(7500, circle.getRadius(), 0.01);
        assertEquals(circle.getOldLocation().distanceTo(circle.getNewLocation()) / 2, circle.getLocation().distanceTo(oldCenter), 10);

        circle.setTime(2);
        circle.setShrinkTransition();
        assertEquals(5000, circle.getRadius(), 0.01);
        assertEquals(0, circle.getLocation().distanceTo(circle.getNewLocation()), 0.01);
    }

    @Test
    public void getShrinkTransitionDoesNotDoAnythingInvalidTime() {
        CircleArea circle = new CircleArea(10000, new GeoPoint(40, 50));
        circle.shrink(0.5);
        circle.setFinalTime(2);
        circle.setTime(-1);
        circle.setShrinkTransition();
        assertEquals(circle.getOldRadius(), circle.getRadius(), 0.01);
        circle.setTime(3);
        circle.setShrinkTransition();
        assertEquals(circle.getOldRadius(), circle.getRadius(), 0.01);
    }

    @Test
    public void displayWorks() {
        CircleArea circle = new CircleArea(10000, new GeoPoint(40, 50));
        MockMap mockMap = new MockMap();
        circle.displayOn(mockMap);
        TestCase.assertTrue(mockMap.getDisplayables().size() == 1);
    }
}
