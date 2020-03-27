package ch.epfl.sdp.map;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.Marker;

public class MapDrawing {
    Marker marker;
    Circle aoe;

    public MapDrawing(Marker marker, Circle circle) {
        this.marker = marker;
        aoe = circle;
    }
}
