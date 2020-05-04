package ch.epfl.sdp.item;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;

/**
 * Represents a box that can store items and can be taken by players.
 */
public class ItemBox extends DetectableEntity {
    private Map<String, Integer> items;
    private boolean taken;

    /**
     * Creates an item box.
     */
    public ItemBox() {
        super(EntityType.ITEMBOX);
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
     * Takes the items from the item box and put them in the user's inventory.
     */
   /* public void take(Player player) {
        taken = true;
        int quantity = 0;
        for (Map.Entry<String, Integer> itemQuant : items.entrySet()) {
            quantity = itemQuant.getValue();

            if (player.getInventory().getItems().containsKey(itemQuant.getKey())) {
                quantity += player.getInventory().getItems().get(itemQuant.getKey());
            }

            player.getInventory().setItemQuantity(itemQuant.getKey(), quantity);
        }
    }*/


    @Override
    public void react(Player player) {
        if(!taken) {
            taken = true;
            int quantity;
            for (Map.Entry<String, Integer> itemQuant : items.entrySet()) {
                quantity = itemQuant.getValue();

                if (player.getInventory().getItems().containsKey(itemQuant.getKey())) {
                    quantity += player.getInventory().getItems().get(itemQuant.getKey());
                }

                player.getInventory().setItemQuantity(itemQuant.getKey(), quantity);
            }

            PlayerManager.getInstance().addPlayerWaitingItems(player);
        }
    }

    @Override
    public boolean isOnce() {
        return true;
    }
}
