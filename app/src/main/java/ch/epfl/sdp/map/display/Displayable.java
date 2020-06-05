package ch.epfl.sdp.map.display;

import ch.epfl.sdp.map.location.Positionable;

/**
 * An interface that all objects that can be displayed on the map should implement
 */
public interface Displayable extends Positionable {
    /**
     * Method for displaying the displayable on the map
     *
     * @param mapApi the API we can use to display the displayable on the map
     */
    void displayOn(MapApi mapApi);

    /**
     * Method for removing the displayable from the map
     *
     * @param mapApi the API we can use the remove the displayable from the map
     */
    default void unDisplayOn(MapApi mapApi) {
        mapApi.removeMarkers(this);
    }
}
