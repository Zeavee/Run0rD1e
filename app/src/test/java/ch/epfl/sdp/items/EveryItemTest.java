package ch.epfl.sdp.items;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.utils.RandomGenerator;

import static org.junit.Assert.assertEquals;

public class EveryItemTest {
    private Item item;
    private String itemName;
    private String itemDescription;

    @Before
    public void setup() {
        RandomGenerator r = new RandomGenerator();
        itemName = r.randomValidString(10);
        itemDescription = r.randomString(60);
        item = new Item(itemName, itemDescription) {

            @Override
            public Item clone() {
                return null;
            }

            @Override
            public void useOn(Player player) {
            }

            @Override
            public double getValue() {
                return 0;
            }
        };
    }

    @Test
    public void itemHasNameAndDescription() {
        assertEquals(itemName, item.getName());
        assertEquals(itemDescription, item.getDescription());
    }
    
}
