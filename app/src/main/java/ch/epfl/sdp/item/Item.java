package ch.epfl.sdp.item;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.GeoPoint;

public abstract class Item {
    /**
     * GeoPoint representing the localisation of the entity
     */
    private String name;
    private String description;
    
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {return this.name;}

    public String getDescription() {return this.description; }

    public abstract void use();
}
