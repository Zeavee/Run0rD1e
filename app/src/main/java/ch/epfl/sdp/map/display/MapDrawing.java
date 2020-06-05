package ch.epfl.sdp.map.display;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;

/**
 * A class that represent the drawing we draw on the map
 */
public class MapDrawing {
    private Polygon polygon;
    private Marker marker;
    private Circle area;
    private final boolean hasPolygon;
    private final boolean hasMarker;
    private final boolean hasCircle;

    /**
     * The constructor for our drawing composed of a marker (icon) and a circle (aoeRadius)
     *
     * @param marker a Google Maps marker we can use to display various types of images
     * @param circle a Google Maps Circle
     */
    MapDrawing(Marker marker, Circle circle) {
        this.marker = marker;
        area = circle;
        hasMarker = true;
        hasCircle = true;
        hasPolygon = false;
    }

    /**
     * The constructor for our drawing composed of a marker (icon)
     *
     * @param marker a Google Maps marker we can use to display various types of images
     */
    MapDrawing(Marker marker) {
        this.marker = marker;
        hasMarker = true;
        hasCircle = false;
        hasPolygon = false;
    }

    /**
     * The constructor for our drawing composed of a marker (icon)
     *
     * @param circle a Google Maps Circle
     */
    MapDrawing(Circle circle) {
        hasMarker = false;
        hasCircle = true;
        hasPolygon = false;
        area = circle;
    }

    /**
     * The constructor for our drawing composed of a marker (icon)
     *
     * @param polygon a Google Maps Circle
     */
    MapDrawing(Polygon polygon) {
        hasMarker = false;
        hasCircle = false;
        hasPolygon = true;
        this.polygon = polygon;
    }

    /**
     * A getter for the marker
     *
     * @return the marker of the drawing
     */
    public Marker getMarker() {
        return marker;
    }

    /**
     * A getter for the aoe
     *
     * @return the circle of the drawing
     */
    public Circle getArea() {
        return area;
    }

    /**
     * A getter for the polygon
     *
     * @return the polygon of the drawing
     */
    public Polygon getPolygon() {
        return polygon;
    }

    /**
     * This method permits to determine if the drawing has a circle
     *
     * @return a boolean that tells if the drawing has a circle
     */
    public boolean hasCircle() {
        return hasCircle;
    }

    /**
     * This method permits to determine if the drawing has a marker
     *
     * @return a boolean that tells if the drawing has a marker
     */
    public boolean hasMarker() {
        return hasMarker;
    }

    /**
     * This method permits to determine if the drawing has a polygon
     *
     * @return a boolean that tells if the drawing has a polygon
     */
    public boolean hasPolygon() {
        return hasPolygon;
    }
}
