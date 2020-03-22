package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.logic.GameArea;
import ch.epfl.sdp.map.GeoPoint;

import static org.junit.Assert.assertEquals;

public class GameAreaTest {

    @Test
    public void shrinkWithNegativeOrTooBigFactorDoesNotDoAnything() {
        GameArea gameArea = new GameArea(100, new GeoPoint(40, 50));
        GameArea newGameArea = gameArea.shrink(-1);
        assertEquals(gameArea.getRadius(), 100, 0.01);
        gameArea.shrink(1.5);
        assertEquals(gameArea.getRadius(), 100, 0.01);
        assertEquals(newGameArea, null);
    }

    @Test
    public void shrinkWorks() {
        GeoPoint oldCenter = new GeoPoint(40, 50);
        GameArea oldGameArea = new GameArea(10000, oldCenter);
        GameArea newGameArea = oldGameArea.shrink(0.5);
        assertEquals(newGameArea.getRadius(), 10000*0.5, 0.01);
        assertEquals(true, oldCenter.distanceTo(newGameArea.getCenter()) < 10000*0.5);
    }

    @Test
    public void getShrinkTransitionWorks() {
        GameArea oldGameArea = new GameArea(10000, new GeoPoint(40, 50));
        GameArea newGameArea = oldGameArea.shrink(0.5);
        GameArea tempGameAreaStart = newGameArea.getShrinkTransition(0, 2, oldGameArea);
        GameArea tempGameAreaMid = newGameArea.getShrinkTransition(1, 2, oldGameArea);
        GameArea tempGameAreaEnd = newGameArea.getShrinkTransition(2, 2, oldGameArea);
        assertEquals(tempGameAreaStart.getRadius(), oldGameArea.getRadius(), 0.01);
        assertEquals(tempGameAreaMid.getRadius(), oldGameArea.getRadius()-newGameArea.getRadius()/2, 0.01);
        assertEquals(tempGameAreaEnd.getRadius(), newGameArea.getRadius(), 0.01);
        assertEquals(0, oldGameArea.getCenter().distanceTo(tempGameAreaStart.getCenter()), 0.01);
        assertEquals(oldGameArea.getCenter().distanceTo(newGameArea.getCenter())/2, oldGameArea.getCenter().distanceTo(tempGameAreaMid.getCenter()), 10);
        assertEquals(0, newGameArea.getCenter().distanceTo(tempGameAreaEnd.getCenter()), 0.01);
    }

    @Test
    public void getShrinkTransitionReturnsNullOnInvalidTime() {
        GameArea oldGameArea = new GameArea(10000, new GeoPoint(40, 50));
        GameArea newGameArea = oldGameArea.shrink(0.5);
        assertEquals(null, newGameArea.getShrinkTransition(-1, 2, oldGameArea));
        assertEquals(null, newGameArea.getShrinkTransition(3, 2, oldGameArea));
        assertEquals(null, newGameArea.getShrinkTransition(0, 2, null));
    }
}
