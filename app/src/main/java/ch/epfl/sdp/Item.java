package ch.epfl.sdp;

public abstract class Item {
    /**
     * GeoPoint representing the localisation of the entity
     */
    GeoPoint location;
    private int itemID;
    private boolean isTaken;

    public Item(GeoPoint location, int itemId, boolean isTaken) {
        this.location = location;
        this.itemID = itemId;
        this.isTaken = isTaken;
    }
}
