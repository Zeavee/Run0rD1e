package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.map.MapDrawing;

import static junit.framework.TestCase.assertNotNull;

public class MapDrawingTest {
    @Test
    public void constructorWorks() {
        MapDrawing mapDrawing = new MapDrawing(null, null);
        assertNotNull(mapDrawing);
    }
}
