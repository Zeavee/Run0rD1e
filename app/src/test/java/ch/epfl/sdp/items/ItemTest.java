package ch.epfl.sdp.items;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.item.Item;

import static org.junit.Assert.assertEquals;

public class ItemTest {
    private Item item;

    @Before
    public void setup() {
        item = new Item("ItemName", "ItemDescription") {

            /**
             * Method to get the type of the object we want to display
             *
             * @return an EntityType which is an enum of types
             */
            @Override
            public EntityType getEntityType() {
                return null;
            }

            @Override
            public Item clone() {
                return null;
            }

            @Override
            public void use() {
            }
        };
    }

    @Test
    public void itemHasNameAndDescription() {
        assertEquals(item.getName(), "ItemName");
        assertEquals(item.getDescription(), "ItemDescription");
    }
}
