package ch.epfl.sdp.map;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;

import org.junit.Test;

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
        assertEquals(false, mapDrawing.hasPolygon());
        assertNull(mapDrawing.getArea());
        assertNull(mapDrawing.getMarker());
        assertNull(mapDrawing.getPolygon());

        MapDrawing mapDrawingOnlyMarker = new MapDrawing((Marker) null);
        assertEquals(true, mapDrawingOnlyMarker.hasMarker());
        assertEquals(false, mapDrawingOnlyMarker.hasCircle());
        assertEquals(false, mapDrawingOnlyMarker.hasPolygon());

        MapDrawing mapDrawingOnlyCircle = new MapDrawing((Circle) null);
        assertEquals(false, mapDrawingOnlyCircle.hasMarker());
        assertEquals(true, mapDrawingOnlyCircle.hasCircle());
        assertEquals(false, mapDrawingOnlyCircle.hasPolygon());

        MapDrawing mapDrawingOnlyPolygon = new MapDrawing((Polygon) null);
        assertEquals(false, mapDrawingOnlyPolygon.hasMarker());
        assertEquals(false, mapDrawingOnlyPolygon.hasCircle());
        assertEquals(true, mapDrawingOnlyPolygon.hasPolygon());
    }
}
