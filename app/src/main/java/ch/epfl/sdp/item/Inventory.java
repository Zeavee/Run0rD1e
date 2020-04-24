package ch.epfl.sdp.item;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents the player inventory.
 */
public class Inventory {
    private Map<Item, Integer> items;

    /**
     * Creates an inventory.
     */
    public Inventory() {
        this.items = new LinkedHashMap<>();
    }

    /**
     * Increases the given item quantity.
     *
     * @param item The item to be added.
     */
    public void addItem(Item item) {
        if (!items.containsKey(item)) {
            items.put(item, 0);
        }

        items.put(item, items.get(item) + 1);
    }

    /**
     * Decreases the given item quantity, if the quantity is positive.
     * @param item The item to be removed.
     */
    public void removeItem(Item item) {
        if (items.containsKey(item)) {
            if(items.get(item) > 0) {
                items.put(item, items.get(item) - 1);
            }
        }
    }

    /**
     * Sets a given quatity to the given item.
     * @param item The item to be set.
     * @param nb The quantity to be set.
     */
    public void setItemQuantity(Item item, int nb) {
        this.items.put(item, nb);
    }

    /**
     * Gets the map of items and quantity.
     *
     * @return A map containing the items and its related quantity.
     */
    public Map<Item, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    /**
     * The amount of items in the inventory.
     * @return An amount representing the sum of quantity of each item.
     */
    public int size(){
        int size = 0;

        for (int value :items.values()) {
            size += value;
        }

        return size;
    }
}

