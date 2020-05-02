package ch.epfl.sdp.items;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.item.Item;

import static org.junit.Assert.assertEquals;

public class ItemTest {
    private Item item;

    @Before
    public void setup() {
        item = new Item("ItemName", "ItemDescription") {

            @Override
            public EntityType getEntityType() {
                return EntityType.NONE;
            }

            @Override
            public void useOn(Player player) {
            }
        };
    }

    @Test
    public void itemHasNameAndDescription() {
        assertEquals(item.getName(), "ItemName");
        assertEquals(item.getDescription(), "ItemDescription");
    }
}
