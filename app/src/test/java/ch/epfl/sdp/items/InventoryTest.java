package ch.epfl.sdp.items;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;

import static org.junit.Assert.assertEquals;

public class InventoryTest {
    @Before
    public void setup() {
        PlayerManager.getInstance().setCurrentUser(new Player("",""));
    }

    @Test
    public void addAndRemoveItemFromInventoryShouldIncreaseAndDecreaseNumberOfItems() {
        Item item1 = new Healthpack(0);
        Item item2 = new Healthpack(1);
        Inventory inventory = new Inventory();

        assertEquals(0, inventory.size());
        inventory.addItem(item1.getName());
        assertEquals(1, inventory.size());
        inventory.addItem(item2.getName());
        assertEquals(2, inventory.size());
        inventory.removeItem(item1.getName());
        assertEquals(1, inventory.size());
        inventory.removeItem(item2.getName());
        assertEquals(0, inventory.size());
    }

    @Test
    public void addUseItemShouldWork() {
        Item item1 = new Healthpack(0);
        Inventory inventory = new Inventory();
        inventory.moveItemToUsedItems("Healthpack 0");
        assertEquals("Healthpack 0", item1.getName());
    }

    @Test
    public void clearItemShouldWork() {
        Item item2 = new Healthpack(0);
        Item item3 = new Healthpack(1);
        Inventory inventory = new Inventory();

        assertEquals(0, inventory.size());
        inventory.addItem(item2.getName());
        assertEquals(1, inventory.size());
        inventory.addItem(item3.getName());
        inventory.clearUsedItems();
        assertEquals(2, inventory.size());
    }

    @Test
    public void sizeShouldWork() {
        Item item5 = new Shield(2);
        Item item6 = new Shrinker(1,1);
        Inventory inventory = new Inventory();
        assertEquals(0, inventory.size());
        assertEquals("Shield 2", item5.getName());
        assertEquals("Shrinker", item6.getName().split(" ")[0]);
    }

    @Test
    public void getUsedItemsShouldReturnCorrectMap() {
        Item item1 = new Healthpack(0);
        Item item2 = new Healthpack(1);
        Inventory inventory = new Inventory();

        inventory.addItem(item1.getName());
        inventory.addItem(item2.getName(), 2);
        inventory.moveItemToUsedItems(item1.getName());

        assertEquals(1, inventory.getUsedItems().size());
    }
}
