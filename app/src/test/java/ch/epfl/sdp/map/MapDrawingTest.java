package ch.epfl.sdp.map;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;

import org.junit.Test;

import ch.epfl.sdp.map.display.MapDrawing;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MapDrawingTest {
    @Test
    public void constructorWorks() {
        MapDrawing mapDrawing = new MapDrawing(null, null);
        assertNotNull(mapDrawing);
        assertTrue(mapDrawing.hasCircle());
        assertTrue(mapDrawing.hasMarker());
        assertFalse(mapDrawing.hasPolygon());
        assertNull(mapDrawing.getArea());
        assertNull(mapDrawing.getMarker());
        assertNull(mapDrawing.getPolygon());

        MapDrawing mapDrawingOnlyMarker = new MapDrawing((Marker) null);
        assertTrue(mapDrawingOnlyMarker.hasMarker());
        assertFalse(mapDrawingOnlyMarker.hasCircle());
        assertFalse(mapDrawingOnlyMarker.hasPolygon());

        MapDrawing mapDrawingOnlyCircle = new MapDrawing((Circle) null);
        assertFalse(mapDrawingOnlyCircle.hasMarker());
        assertTrue(mapDrawingOnlyCircle.hasCircle());
        assertFalse(mapDrawingOnlyCircle.hasPolygon());

        MapDrawing mapDrawingOnlyPolygon = new MapDrawing((Polygon) null);
        assertFalse(mapDrawingOnlyPolygon.hasMarker());
        assertFalse(mapDrawingOnlyPolygon.hasCircle());
        assertTrue(mapDrawingOnlyPolygon.hasPolygon());
    }
}
