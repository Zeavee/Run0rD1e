package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.map.MapDrawing;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MapDrawingTest {
    @Test
    public void constructorWorks() {
        MapDrawing mapDrawing = new MapDrawing(null, null);
        assertNotNull(mapDrawing);
        assertEquals(true, mapDrawing.hasCircle());
        assertEquals(true, mapDrawing.hasMarker());
        assertNull(mapDrawing.getAoe());
        assertNull(mapDrawing.getMarker());

        MapDrawing mapDrawingOnlyMarker = new MapDrawing(null);
        assertEquals(true, mapDrawing.hasMarker());
        assertEquals(false, mapDrawing.hasCircle());
    }
}
