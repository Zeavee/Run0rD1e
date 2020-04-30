package ch.epfl.sdp.map;

import ch.epfl.sdp.geometry.GeoPoint;

public class MockLocationFinder implements LocationFinder {

    private GeoPoint currentLocation = new GeoPoint(40, 50);

    public void setCurrentLocation(GeoPoint currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public GeoPoint getCurrentLocation() {
        return null;
    }
}
