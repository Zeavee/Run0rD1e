package ch.epfl.sdp.geometry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RectangleAreaTest {

    @Test
    public void shrinkWithNegativeOrTooBigFactorDoesNotDoAnything() {
        RectangleArea rectangleArea = new RectangleArea(10, 20, new GeoPoint(40, 50));
        rectangleArea.shrink(-1);
        assertEquals(rectangleArea.getHeight(), 10, 0.01);
        assertEquals(rectangleArea.getWidth(), 20, 0.01);
        rectangleArea.shrink(1.5);
        assertEquals(rectangleArea.getHeight(), 10, 0.01);
        assertEquals(rectangleArea.getWidth(), 20, 0.01);
        assertFalse(rectangleArea.isShrinking());
    }

    @Test
    public void shrinkWorks() {
        GeoPoint oldCenter = new GeoPoint(40, 50);
        RectangleArea rectangleArea = new RectangleArea(10000, 20000, oldCenter);
        rectangleArea.shrink(0.5);
        assertEquals(rectangleArea.getNewHeight(), 10000 * 0.5, 0.01);
        assertEquals(rectangleArea.getNewWidth(), 20000 * 0.5, 0.01);
        assertTrue(Math.abs(oldCenter.toVector().x() - rectangleArea.getNewLocation().toVector().x()) < 10000 * 0.5);
        assertTrue(Math.abs(oldCenter.toVector().y() - rectangleArea.getNewLocation().toVector().y()) < 5000 * 0.5);
        assertEquals(rectangleArea.getOldHeight(), 10000, 0.01);
        assertEquals(rectangleArea.getOldWidth(), 20000, 0.01);
        assertTrue(oldCenter.distanceTo(rectangleArea.getOldLocation()) < 0.01);
        rectangleArea.finishShrink();
        assertFalse(rectangleArea.isShrinking());
        assertEquals(rectangleArea.getHeight(), 10000 * 0.5, 0.01);
        assertEquals(rectangleArea.getWidth(), 20000 * 0.5, 0.01);
        assertTrue(rectangleArea.getLocation().distanceTo(rectangleArea.getNewLocation()) < 0.01);
    }

    @Test
    public void randomLocationWorks() {
        GeoPoint center = new GeoPoint(40, 40);
        RectangleArea rectangleArea = new RectangleArea(1000, 2000, center);
        GeoPoint randomLocation = rectangleArea.randomLocation();
        assertTrue(rectangleArea.isInside(randomLocation));
    }

    @Test
    public void getShrinkTransitionWorks() {
        GeoPoint oldCenter = new GeoPoint(40, 50);
        RectangleArea rectangleArea = new RectangleArea(10000, 20000, oldCenter);
        rectangleArea.shrink(0.5);
        rectangleArea.setTime(0);
        rectangleArea.setFinalTime(2);
        rectangleArea.setShrinkTransition();
        assertEquals(0, rectangleArea.getLocation().distanceTo(oldCenter), 0.01);

        rectangleArea.setTime(1);
        rectangleArea.setShrinkTransition();
        assertEquals(7500, rectangleArea.getHeight(), 0.01);
        assertEquals(15000, rectangleArea.getWidth(), 0.01);
        assertEquals(rectangleArea.getOldLocation().distanceTo(rectangleArea.getNewLocation()) / 2, rectangleArea.getLocation().distanceTo(oldCenter), 10);

        rectangleArea.setTime(2);
        rectangleArea.setShrinkTransition();
        assertEquals(5000, rectangleArea.getHeight(), 0.01);
        assertEquals(10000, rectangleArea.getWidth(), 0.01);
        assertEquals(0, rectangleArea.getLocation().distanceTo(rectangleArea.getNewLocation()), 0.01);
    }

    @Test
    public void getShrinkTransitionDoesNotDoAnythingOnInvalidTime() {
        RectangleArea rectangleArea = new RectangleArea(10000, 20000, new GeoPoint(40, 50));
        rectangleArea.shrink(0.5);
        rectangleArea.setFinalTime(2);
        rectangleArea.setTime(-1);
        rectangleArea.setShrinkTransition();
        assertEquals(rectangleArea.getOldHeight(), rectangleArea.getHeight(), 0.01);
        assertEquals(rectangleArea.getOldWidth(), rectangleArea.getWidth(), 0.01);
        rectangleArea.setTime(3);
        rectangleArea.setShrinkTransition();
        assertEquals(rectangleArea.getOldHeight(), rectangleArea.getHeight(), 0.01);
        assertEquals(rectangleArea.getOldWidth(), rectangleArea.getWidth(), 0.01);
    }

    @Test
    public void updateRectangleAreaWorks() {
        RectangleArea rectangleArea = new RectangleArea(1000, 2000, new GeoPoint(30, 30));
        RectangleArea newRectangleArea = new RectangleArea(10000, 20000, new GeoPoint(40, 40));
        rectangleArea.updateGameArea(newRectangleArea);
        assertEquals(rectangleArea.getHeight(), newRectangleArea.getHeight(), 0.01);
        assertEquals(rectangleArea.getWidth(), newRectangleArea.getWidth(), 0.01);
        assertTrue(rectangleArea.getLocation().distanceTo(newRectangleArea.getLocation()) < 0.01);
    }
}
