package ch.epfl.sdp;

public abstract class Item {
    /**
     * GeoPoint representing the localisation of the entity
     */
    GeoPoint location;
    private String name;
    private boolean isTaken;
    private String description;


    
    public Item(GeoPoint location, String name, boolean isTaken, String description) {
        this.location = location;
        this.name = name;
        this.isTaken = isTaken;
        this.description = description;
    }

    public void takeItem() {this.isTaken = true;}

    public String getDescription() {return this.description; }

    public boolean isTaken() {return isTaken;}

    public String getName() {return this.name;}
}
