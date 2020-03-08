package ch.epfl.sdp;

public abstract class Items {
    /**
     * GeoPoint representing the localisation of the entity
     */
    GeoPoint location;
    private int itemID;
    private boolean isTaken;

    public Items(GeoPoint location, int itemId, boolean isTaken) {
        this.location = location;
        this.itemID = itemId;
        this.isTaken = isTaken;
    }
}
