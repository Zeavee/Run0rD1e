package ch.epfl.sdp.item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the player inventory.
 */
public class Inventory {
    /**
     * Map from item name to quantity
     */
    private Map<String, Integer> items;
    private Map<String, Integer> usedItems;

    /**
     * Creates an inventory.
     */
    public Inventory() {
        this.items = new HashMap<>();
        this.usedItems = new HashMap<>();
    }

    /**
     * Increases the given item quantity.
     *
     * @param itemName The name of the item to be added.
     */
    public void addItem(String itemName) {
        if(items.containsKey(itemName)) {
            items.put(itemName, 1 + items.get(itemName));
        }else {
            items.put(itemName, 1);
        }
    }

    /**
     * Decreases the given item quantity, if the quantity is positive.
     * @param itemName The name of the item to be removed.
     */
    public void removeItem(String itemName) {
        if (items.containsKey(itemName)) {
            if(items.get(itemName) > 1) {
                items.put(itemName, items.get(itemName) - 1);
            }else{
                items.remove(itemName);
            }
        }
    }

    /**
     * Sets a given quatity to the given item.
     * @param itemName The  name of the item to be set.
     * @param nb The quantity to be set.
     */
    public void setItemQuantity(String itemName, int nb) {
        this.items.put(itemName, nb);
    }

    /**
     * Gets the map of items and quantity.
     *
     * @return A map containing the items and its related quantity.
     */
    public Map<String, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    /**
     * Set the map of items and quantity.
     *
     * @return A map containing the items and its related quantity.
     */
    public void setItems(Map<String, Integer> items){
        this.items = items;
    }

    /**
     * Use an item and put it in the used item map for the database.
     * @param itemName A name referring to the item.
     */
    public void useItem(String itemName){
        if(items.containsKey(itemName)){
            removeItem(itemName);
            if(usedItems.containsKey(itemName)) {
                usedItems.put(itemName, 1 + usedItems.get(itemName));
            }else {
                usedItems.put(itemName, 1);
            }
        }
    }

    /**
     * Gets the map of used items and quantity.
     *
     * @return A map containing the used items and its related quantity.
     */
    public Map<String, Integer> getUsedItems() {
        return Collections.unmodifiableMap(usedItems);
    }

    public void clearUsedItems(){
        usedItems.clear();
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

