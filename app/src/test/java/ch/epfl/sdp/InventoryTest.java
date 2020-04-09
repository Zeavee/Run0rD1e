package ch.epfl.sdp;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Inventory;
import ch.epfl.sdp.item.Item;

import static org.junit.Assert.assertEquals;

public class InventoryTest {
    @Before
    public void setup() {
        PlayerManager.setUser(new Player());
    }

    @Test
    public void addAndRemoveItemFromInventoryShouldIncreaseAndDecreaseNumberOfItems() {
        Item item1 = new Healthpack(0);
        Item item2 = new Healthpack(1);
        Inventory inventory = new Inventory();

        assertEquals(0, inventory.size());
        inventory.addItem(item1);
        assertEquals(1, inventory.size());
        inventory.addItem(item2);
        assertEquals(2, inventory.size());
        inventory.removeItem(item1);
        assertEquals(1, inventory.size());
        inventory.removeItem(item2);
        assertEquals(0, inventory.size());
    }
}
