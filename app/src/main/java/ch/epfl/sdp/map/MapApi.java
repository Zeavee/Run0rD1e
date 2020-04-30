package ch.epfl.sdp.map;

import ch.epfl.sdp.geometry.GeoPoint;

/**
 * This interface shows the API we can use to display objects on the map
 */
public interface MapApi {
    /**
     * A method that moves the camera on the given location
     */
    void moveCameraOnLocation(GeoPoint location);

    /**
     * A method that display a small icon on the map
     * The size of the icon never changes on the screen
     * @param displayable the object we want to display
     * @param title the String we want to add with the object
     * @param id the icon we want to use
     */
    void displaySmallIcon(Displayable displayable, String title, int id);

    /**
     * A method that display a two circles
     * One of them show the aoe radius and the other shows the more precise position
     * @param displayable the object we want to display
     * @param color the color of the circles
     * @param title the String we want to add with the object
     * @param aoeRadius the aoeRadius of the displayable
     */
    void displayMarkerCircle(Displayable displayable, int color, String title, int aoeRadius);

    /**
     * This methods remove a given object from the map
     * @param displayable the object we want to remove from the map
     */
    void removeMarkers(Displayable displayable);
}
