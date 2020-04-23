package ch.epfl.sdp.item;

public abstract class Item implements Cloneable {
    private String name;
    private String description;
    
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public abstract Item clone();

    public String getName() {return this.name;}

    public String getDescription() {return this.description; }

    public abstract void use();
}
