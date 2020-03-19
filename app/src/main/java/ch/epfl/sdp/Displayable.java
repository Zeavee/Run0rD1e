package ch.epfl.sdp;

interface Displayable {
    /**
     * Method for getting the location for displaying on the map
     * @return a GeoPoint which is a location
     */
    public GeoPoint getLocation();

    /**
     * Method to get the type of the object we want to display
     * @return an EntityType which is an enum of types
     */
    public EntityType getEntityType();
}
