package ch.epfl.sdp.item;

public abstract class Item {
    private String name;
    private String description;
    
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Item(Item item) {

    }

    public abstract Item createCopy();

    public String getName() {return this.name;}

    public String getDescription() {return this.description; }

    public abstract void use();
}
