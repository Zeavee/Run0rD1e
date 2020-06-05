package ch.epfl.sdp.items;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entities.Entity;
import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.MapApi;

/**
 * Represents a box that can store items and can be taken by players.
 */
public class ItemBox extends Entity implements Updatable {
    private final Map<String, Integer> items;
    private boolean taken;
    private boolean isDisplayed;
    private boolean reDisplay;

    /**
     * Creates an item box.
     */
    public ItemBox(GeoPoint location) {
        super(location);
        this.items = new HashMap<>();
        taken = false;
        isDisplayed = false;
        reDisplay = false;
    }

    public void setReDisplay(boolean reDisplay) {
        this.reDisplay = reDisplay;
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
        PlayerManager.getInstance().addPlayerWaitingItems(player);
    }

    /**
     * Method for displaying the displayable on the map
     *
     * @param mapApi the API we can use to display the displayable on the map
     */
    @Override
    public void displayOn(MapApi mapApi) {
        // The location of the itemBox will never change, we only need to display once
        if (!isDisplayed || reDisplay) {
            mapApi.displaySmallIcon(this, "ItemBox", R.drawable.itembox);
            isDisplayed = true;
            reDisplay = false;
        }
    }

    @Override
    public void update() {
        if(taken) {
            return;
        }

        PlayerManager playerManager = PlayerManager.getInstance();
        for (Player player : playerManager.getPlayers()) {
            if (this.getLocation().distanceTo(player.getLocation()) - player.getAoeRadius() >= 1) {
                continue;
            }

            taken = true;
            react(player);
            Game.getInstance().removeCurrentFromUpdateList();
            Game.getInstance().removeFromDisplayList(this);
            break;
        }
    }
}