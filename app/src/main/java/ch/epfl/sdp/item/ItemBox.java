package ch.epfl.sdp.item;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.map.MapApi;

/**
 * Represents a box that can store items and can be taken by players.
 */
public class ItemBox extends DetectableEntity {
    private Map<Item, Integer> items;
    private boolean taken;

    /**
     * Creates an item box.
     */
    public ItemBox() {
        super();
        this.items = new HashMap<>();
        taken = false;
    }

    /**
     * Put items in the item box.
     *
     * @param item     The item to be stored in the item box.
     * @param quantity The quantity of the item to be stored.
     */
    public void putItems(Item item, int quantity) {
        items.put(item, quantity);
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
     * Takes the items from the item box and put them in the user's inventory.
     */
    public void take() {
        if (!isTaken()) {
            taken = true;
            Inventory inventory = PlayerManager.getCurrentUser().getInventory();
            int quantity = 0;
            for (Map.Entry<Item, Integer> itemQuant : items.entrySet()) {
                quantity = itemQuant.getValue();

                if (inventory.getItems().get(itemQuant.getKey()) != null) {
                    quantity += inventory.getItems().get(itemQuant.getKey());
                }

                inventory.setItemQuantity(itemQuant.getKey(), quantity);
            }
        }
    }

    @Override
    public void react(Player player) {
        take();
    }

    @Override
    public void displayOn(MapApi mapApi) {
        mapApi.displaySmallIcon(this, "ItemBox", R.drawable.itembox);
    }

    @Override
    public boolean isOnce() {
        return true;
    }


}
