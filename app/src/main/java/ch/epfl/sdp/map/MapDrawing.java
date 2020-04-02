package ch.epfl.sdp.map;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.Marker;

public class MapDrawing {
    private Marker marker;
    private Circle aoe;
    private boolean hasMarker;
    private boolean hasCircle;

    public MapDrawing(Marker marker, Circle circle) {
        this.marker = marker;
        aoe = circle;
        hasMarker = true;
        hasCircle = true;
    }

    public MapDrawing(Marker marker) {
        this.marker = marker;
        hasMarker = true;
        hasCircle = false;
    }

    public Marker getMarker() {
        return marker;
    }

    public Circle getAoe() {
        return aoe;
    }

    public boolean hasCircle() {
        return hasCircle;
    }

    public boolean hasMarker() {
        return hasMarker;
    }
}
