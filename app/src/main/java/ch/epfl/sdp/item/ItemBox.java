package ch.epfl.sdp.item;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapApi;

/**
 * Represents a box that can store items and can be taken by players.
 */
public class ItemBox implements Displayable, Updatable {
    private Map<String, Integer> items;
    private GeoPoint location;
    private boolean taken;
    private boolean isDisplayed;

    /**
     * Creates an item box.
     */
    public ItemBox(GeoPoint location) {
        this.items = new HashMap<>();
        this.location = location;
        taken = false;
        isDisplayed = false;
    }

    /**
     * Put items in the item box.
     *
     * @param item     The item to be stored in the item box.
     * @param quantity The quantity of the item to be stored.
     */
    public void putItems(Item item, int quantity) {
        items.put(item.getName(), quantity);
    }

    /**
     * Return true if the item box has been taken.
     *
     * @return True if the item box has been taken.
     */
    public boolean isTaken() {
        return taken;
    }

    /**
     * Reacts to a detection.
     *
     * @param player The player that has detected the entity.
     */
    public void react(Player player) {
        for (Map.Entry<String, Integer> itemQuant : items.entrySet()) {
            player.getInventory().addItem(itemQuant.getKey(), itemQuant.getValue());
        }
        taken = true;
        PlayerManager.getInstance().addPlayerWaitingItems(player);
    }

    /**
     * Method for displaying the displayable on the map
     *
     * @param mapApi the API we can use to display the displayable on the map
     */
    @Override
    public void displayOn(MapApi mapApi) {
        // The locatioon of the itemBox will never change, we only need to display once
        if(!isDisplayed) {
            mapApi.displaySmallIcon(this, "ItemBox", R.drawable.itembox);
            isDisplayed = true;
        }
    }

    @Override
    public void update() {
        if (!taken) {
            PlayerManager playerManager = PlayerManager.getInstance();
            for (Player player : playerManager.getPlayers()) {
                if (this.getLocation().distanceTo(player.getLocation()) - player.getAoeRadius() < 1) {
                    react(player);
                    Game.getInstance().removeCurrentFromUpdateList();
                    Game.getInstance().removeFromDisplayList(this);
                    break;
                }
            }
        }
    }

    @Override
    public GeoPoint getLocation() {
        return location;
    }

    /**
     * Sets the location of the entity on the geodesic surface.
     *
     * @param location The location on the geodesic surface.
     */
    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}
