package ch.epfl.sdp.logic;

import org.junit.Test;

import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.CircleArea;

import static org.junit.Assert.assertEquals;

public class GameAreaTest {

    @Test
    public void shrinkWithNegativeOrTooBigFactorDoesNotDoAnything() {
        CircleArea circleArea = new CircleArea(100, new GeoPoint(40, 50));
        CircleArea newCircleArea = circleArea.shrink(-1);
        assertEquals(circleArea.getRadius(), 100, 0.01);
        circleArea.shrink(1.5);
        assertEquals(circleArea.getRadius(), 100, 0.01);
        assertEquals(newCircleArea, null);
    }

    @Test
    public void shrinkWorks() {
        GeoPoint oldCenter = new GeoPoint(40, 50);
        CircleArea oldCircleArea = new CircleArea(10000, oldCenter);
        CircleArea newCircleArea = oldCircleArea.shrink(0.5);
        assertEquals(newCircleArea.getRadius(), 10000*0.5, 0.01);
        assertEquals(true, oldCenter.distanceTo(newCircleArea.getCenter()) < 10000*0.5);
    }

    @Test
    public void getShrinkTransitionWorks() {
        CircleArea oldCircleArea = new CircleArea(10000, new GeoPoint(40, 50));
        CircleArea newCircleArea = oldCircleArea.shrink(0.5);
        CircleArea tempCircleAreaStart = newCircleArea.getShrinkTransition(0, 2, oldCircleArea);
        CircleArea tempCircleAreaMid = newCircleArea.getShrinkTransition(1, 2, oldCircleArea);
        CircleArea tempCircleAreaEnd = newCircleArea.getShrinkTransition(2, 2, oldCircleArea);
        assertEquals(tempCircleAreaStart.getRadius(), oldCircleArea.getRadius(), 0.01);
        assertEquals(tempCircleAreaMid.getRadius(), oldCircleArea.getRadius()- newCircleArea.getRadius()/2, 0.01);
        assertEquals(tempCircleAreaEnd.getRadius(), newCircleArea.getRadius(), 0.01);
        assertEquals(0, oldCircleArea.getCenter().distanceTo(tempCircleAreaStart.getCenter()), 0.01);
        assertEquals(oldCircleArea.getCenter().distanceTo(newCircleArea.getCenter())/2, oldCircleArea.getCenter().distanceTo(tempCircleAreaMid.getCenter()), 10);
        assertEquals(0, newCircleArea.getCenter().distanceTo(tempCircleAreaEnd.getCenter()), 0.01);
    }

    @Test
    public void getShrinkTransitionReturnsNullOnInvalidTime() {
        CircleArea oldCircleArea = new CircleArea(10000, new GeoPoint(40, 50));
        CircleArea newCircleArea = oldCircleArea.shrink(0.5);
        assertEquals(null, newCircleArea.getShrinkTransition(-1, 2, oldCircleArea));
        assertEquals(null, newCircleArea.getShrinkTransition(3, 2, oldCircleArea));
        assertEquals(null, newCircleArea.getShrinkTransition(0, 2, null));
    }
}
