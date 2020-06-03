package ch.epfl.sdp.utils;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.map.Renderer;
import ch.epfl.sdp.market.Market;

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
        Log.d("mockMapApi", "display");
        for (Displayable displayable : displayables) {
            if (displayable instanceof Market) {
                Log.d("mock map", "being displayed");
            }
            displayable.displayOn(Game.getInstance().getMapApi());
        }
    }

    @Override
    public void unDisplay(Displayable displayable) {
        displayable.unDisplayOn(this);
    }
}
