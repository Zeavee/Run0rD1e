package ch.epfl.sdp.item;

import java.util.LinkedHashMap;

public class Inventory {
    private LinkedHashMap<Item, Integer> items;

    public Inventory() {
        this.items = new LinkedHashMap<>();
    }

    public void addItem(Item item) {
        if (!items.containsKey(item)) {
            items.put(item, 0);
        }

        items.put(item, items.get(item) + 1);
    }

    public void removeItem(Item item) {
        if (items.containsKey(item)) {
            if(items.get(item) > 0) {
                items.put(item, items.get(item) - 1);
            }
        }
    }

    public void setItemQuantity(Item item, int nb) {
        this.items.put(item, nb);
    }

    public LinkedHashMap<Item, Integer> getItems() {
        return items;
    }

    public int size(){
        int size = 0;

        for (int value :items.values()) {
            size += value;
        }

        return size;
    }
}

