package ch.epfl.sdp.map;

import ch.epfl.sdp.geometry.GeoPoint;

public interface Displayable {
    /**
     * Method for getting the location for displaying on the map
     * @return a GeoPoint which is a location
     */
    GeoPoint getLocation();

    /**
     * Method for displaying the displayable on the map
     * @param mapApi the API we can use to display the displayable on the map
     */
    void displayOn(MapApi mapApi);

    /**
     * Method for removing the displayable from the map
     * @param mapApi the API we can use the remove the displayable from the map
     */
    default void unDisplayOn(MapApi mapApi) {
        mapApi.removeMarkers(this);
    }

    /**
     * Method to decide if the entity must be displayed only once.
     *
     * @return True if the entity should be displayed only once.
     */
    boolean isOnce();
}
