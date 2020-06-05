package ch.epfl.sdp.map;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.epfl.sdp.map.display.Displayable;
import ch.epfl.sdp.map.display.MapApi;
import ch.epfl.sdp.map.display.Renderer;
import ch.epfl.sdp.map.location.GeoPoint;

public class MockMap implements MapApi, Renderer {
    // Used for tests
    private final ArrayList<Displayable> displayables = new ArrayList<>();

    public ArrayList<Displayable> getDisplayables() {
        return displayables;
    }

    @Override
    public void moveCameraOnLocation(GeoPoint location) {

    }

    @Override
    public void displaySmallIcon(Displayable displayable, String title, int id) {
        displayables.add(displayable);
    }

    @Override
    public void displayMarkerCircle(Displayable displayable, int yellow, String other_player, int i) {
        displayables.add(displayable);
    }

    @Override
    public void displayCircle(Displayable displayable, int color, int radius, int alpha) {
        displayables.add(displayable);
    }

    @Override
    public void removeMarkers(Displayable displayable) {
        displayables.remove(displayable);
    }

    @Override
    public void displayPolygon(Displayable displayable, List<LatLng> vertices, int strokeColor, int fillColor) {
        displayables.add(displayable);
    }

    @Override
    public void display(Collection<Displayable> displayables) {
        for (Displayable displayable : displayables) {
            displayable.displayOn(this);
        }
    }

    @Override
    public void unDisplay(Displayable displayable) {
        displayable.unDisplayOn(this);
    }
}
