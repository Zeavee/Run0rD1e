package ch.epfl.sdp.map;

import ch.epfl.sdp.geometry.GeoPoint;

public interface MapApi {
    /**
     * A method that moves the camera on the given location
     */
    void moveCameraOnLocation(GeoPoint location);

    void displaySmallIcon(Displayable displayable, String title, int id);

    void displayMarkerCircle(Displayable displayable, int yellow, String other_player, int i);

    void removeMarkers(Displayable displayable);
}
