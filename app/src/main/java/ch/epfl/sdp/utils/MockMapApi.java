package ch.epfl.sdp.utils;

import android.util.Log;


import java.util.ArrayList;
import java.util.Collection;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.map.Renderer;

public class MockMapApi implements MapApi, Renderer {
    // Used for tests
    private ArrayList<Displayable> displayables = new ArrayList<>();

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
    public void removeMarkers(Displayable displayable) {
        displayables.remove(displayable);
    }


    /**
     * A method that display a list of object on the map
     *
     * @param displayables the list we want to display
     */
    @Override
    public void display(Collection<Displayable> displayables) {
        // "In a parallel universe, items can be displayed on a mock map"
    }
}
