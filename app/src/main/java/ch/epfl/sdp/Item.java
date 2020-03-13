package ch.epfl.sdp;

public abstract class Item {
    /**
     * GeoPoint representing the localisation of the entity
     */
    GeoPoint location;
    private int itemID;
    private boolean isTaken;
    private String description;


    public Item(GeoPoint location, int itemId, boolean isTaken, String description) {
        this.location = location;
        this.itemID = itemId;
        this.isTaken = isTaken;
        this.description = description;
    }

    public void takeItem() {
        this.isTaken = true;
    }

    public int getItemID() {
        return this.itemID;
    }

    public String getDescription() {return this.description; }

    public boolean isTaken() {return isTaken;}
}
