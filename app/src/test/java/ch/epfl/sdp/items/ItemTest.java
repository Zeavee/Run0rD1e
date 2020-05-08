package ch.epfl.sdp.items;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.item.Item;

import static org.junit.Assert.assertEquals;

public class ItemTest {
    private Item item;

    @Before
    public void setup() {
        item = new Item("ItemName", "ItemDescription") {

            @Override
            public Item clone() {
                return null;
            }

            @Override
            public void use() {
            }

            @Override
            public double getValue() {
                return 0;
            }
        };
    }

    @Test
    public void itemHasNameAndDescription() {
        assertEquals(item.getName(), "ItemName");
        assertEquals(item.getDescription(), "ItemDescription");
    }
}
