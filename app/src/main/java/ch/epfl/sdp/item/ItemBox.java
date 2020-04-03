package ch.epfl.sdp.item;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;

public class ItemBox extends DetectableEntity {
    private Map<Item, Integer> items;

    public ItemBox(){
        super(EntityType.ITEMBOX);
        this.items = new HashMap<>();
    }

    public void putItems(Item item, int quantity){
        items.put(item, quantity);
    }

    public boolean isTaken() {
        return super.isActive();
    }

    @Override
    public void react(Player player) {
        Inventory inventory = player.getInventory();
        for (Map.Entry<Item, Integer> itemQuant: items.entrySet()) {
            inventory.setItemQuantity(itemQuant.getKey(), inventory.getItems().get(itemQuant.getKey()) + itemQuant.getValue());
        }
    }
}
