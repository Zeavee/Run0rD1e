package ch.epfl.sdp.database.firebase.entityForFirebase;

/**
 * The itemBox entity to be stored in cloud firebase
 */
public class ItemBoxForFirebase {
    private String id;
    private GeoPointForFirebase location;
    private boolean taken;

    /**
     * For Firebase each custom class must have a public constructor that takes no arguments
     */
    public ItemBoxForFirebase() {
    }

    /**
     * Construct a itemBoxForFirebase instance
     *
     * @param id       The unique id of the itemBoxForFirebase
     * @param location The location of the itemBoxForFirebase
     * @param taken    A boolean indicates whether this itemBox has been taken by the players or not
     */
    public ItemBoxForFirebase(String id, GeoPointForFirebase location, boolean taken) {
        this.id = id;
        this.location = location;
        this.taken = taken;
    }

    /**
     * Return true if this itemBox has been take, otherwise return false
     *
     * @return A boolean indicates whether this itemBox has been take by the players or not.
     */
    public boolean isTaken() {
        return taken;
    }

    /**
     * Set whether this itemBox has been taken by the players or not
     *
     * @param taken A boolean indicates whether this itemBox has been take by the players or not.
     */
    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    /**
     * Get the location of the itemsForFirebase
     *
     * @return The location of the itemsForFirebase
     */
    public GeoPointForFirebase getLocation() {
        return location;
    }

    /**
     * Set the location of the itemsForFirebase
     *
     * @param location The location of the itemsForFirebase
     */
    public void setLocation(GeoPointForFirebase location) {
        this.location = location;
    }

    /**
     * Get the unique id of the itemsForFirebase
     *
     * @return The unique id of the itemsForFirebase
     */
    public String getId() {
        return id;
    }

    /**
     * Set the unique id of the itemsForFirebase
     *
     * @param id The unique id of the itemsForFirebase
     */
    public void setId(String id) {
        this.id = id;
    }
}
