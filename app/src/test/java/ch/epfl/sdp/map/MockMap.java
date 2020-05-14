package ch.epfl.sdp.map;

import java.util.ArrayList;
import java.util.Collection;

import ch.epfl.sdp.geometry.GeoPoint;

public class MockMap implements MapApi, Renderer, LocationFinder {
    // Used for tests
    private ArrayList<Displayable> displayables = new ArrayList<>();
    private ArrayList<Displayable> currentlyDisplayed = new ArrayList<>();
    private GeoPoint location;

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
    public void displayCircle(Displayable displayable, int color, int radius) {
        displayables.add(displayable);
    }

    @Override
    public void removeMarkers(Displayable displayable) {
        displayables.remove(displayable);
    }

    @Override
    public GeoPoint getCurrentLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    @Override
    public void display(Collection<Displayable> displayables) {
        currentlyDisplayed.addAll(displayables);
    }

    @Override
    public void unDisplay(Displayable displayable) {
        currentlyDisplayed.remove(displayable);
    }
}
