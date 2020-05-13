package ch.epfl.sdp.geometry;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RectangleAreaTest {
    @Test
    public void testGetters() {
        RectangleArea rectangleArea = new RectangleArea(10, 20, new GeoPoint(10, 20));
        assertTrue(rectangleArea.getHeight() == 10);
        assertTrue(rectangleArea.getWidth() == 20);
    }
}
