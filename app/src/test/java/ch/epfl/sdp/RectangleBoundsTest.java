package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.RectangleBounds;

import static org.junit.Assert.assertTrue;

public class RectangleBoundsTest {
    @Test
    public void testGetters() {
        RectangleBounds rectangleBounds = new RectangleBounds(10, 20);
        assertTrue(rectangleBounds.getHeight() == 10);
        assertTrue(rectangleBounds.getWidth() == 20);
    }
}
