package ch.epfl.sdp.map.location;

/**
 * This interface should be implemented by all classes that represents something having a position in the game
 */
public interface Positionable {
    /**
     * Method for getting the location for displaying on the map
     *
     * @return a GeoPoint which is a location
     */
    GeoPoint getLocation();
}
