package ch.epfl.sdp.item;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;

public class ItemBox extends DetectableEntity {
    private Map<Item, Integer> items;
    private boolean taken;

    public ItemBox(){
        super(EntityType.ITEMBOX);
        this.items = new HashMap<>();
        taken = false;
    }

    public void putItems(Item item, int quantity){
        items.put(item, quantity);
    }

    public boolean isTaken() {
        return taken;
    }

    @Override
    public void react(Player player) {
        taken = true;
        Inventory inventory = player.getInventory();
        int quantity = 0;
        for (Map.Entry<Item, Integer> itemQuant: items.entrySet()) {
            quantity = itemQuant.getValue();

            if(inventory.getItems().get(itemQuant.getKey()) != null){
                quantity += inventory.getItems().get(itemQuant.getKey());
            }

            inventory.setItemQuantity(itemQuant.getKey(), quantity);
        }
    }

    @Override
    public boolean once() {
        return true;
    }
}
