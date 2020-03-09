package ch.epfl.sdp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameAreaTest {

    @Test
    public void shrinkWithNegativeOrTooBigFactorDoesNotDoAnything() {
        GameArea gameArea = new GameArea(100, new GeoPoint(40, 50));
        gameArea.shrink(-1);
        assertEquals(gameArea.getRadius(), 100, 0.01);
        gameArea.shrink(1.5);
        assertEquals(gameArea.getRadius(), 100, 0.01);
    }

    @Test
    public void shrinkWorks() {
        GeoPoint oldCenter = new GeoPoint(40, 50);
        GameArea gameArea = new GameArea(10000, oldCenter);
        gameArea.shrink(0.5);
        assertEquals(gameArea.getRadius(), 10000*0.5, 0.01);
        assertEquals(true, oldCenter.distanceTo(gameArea.getCenter()) < 10000*0.5);
    }
}
