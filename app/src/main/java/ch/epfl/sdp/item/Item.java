package ch.epfl.sdp.item;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.GeoPoint;

import static ch.epfl.sdp.map.MapsActivity.mapApi;

public abstract class Item implements Displayable, Updatable {
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
        Game.addToUpdateList(this);
        Game.addToDisplayList(this);
    }

    public void takeItem() {
        this.isTaken = true;
        Game.removeFromUpdateList(this);
        Game.removeFromDisplayList(this);
        mapApi.getActivity().runOnUiThread(() -> mapApi.unDisplayEntity(this));
    }

    public String getDescription() {return this.description; }

    public boolean isTaken() {return isTaken;}

    public String getName() {return this.name;}

    @Override
    public GeoPoint getLocation() {
        return location;
    }

    @Override
    public void update() {
        for (Player player: PlayerManager.getPlayers()) {
            if (this.getLocation().distanceTo(player.getLocation()) - player.getAoeRadius() < 0 && !isTaken) {
                player.getInventory().addItem(name);
                takeItem();
            }
        }
    }

     @Override
    public EntityType getEntityType() {
        return EntityType.ITEM;
     }

}
