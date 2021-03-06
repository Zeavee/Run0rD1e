package ch.epfl.sdp.items;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ItemFactoryTest {

    private ItemFactory itemFactory;

    @Before
    public void setUp() {
        itemFactory = new ItemFactory();
    }

    @Test
    public void getItemShouldGenerateHealthpack() {
        Item item = itemFactory.getItem("Healthpack 100");
        assertTrue(item instanceof Healthpack);
    }

    @Test
    public void getItemShouldGenerateShield() {
        Item item = itemFactory.getItem("Shield 100");
        assertTrue(item instanceof Shield);
    }

    @Test
    public void getItemShouldGenerateShrinker() {
        Item item = itemFactory.getItem("Shrinker 1 2");
        assertTrue(item instanceof Shrinker);
    }

    @Test
    public void getItemShouldGeneratePhantom() {
        Item item = itemFactory.getItem("Phantom 100");
        assertTrue(item instanceof Phantom);
    }

    @Test
    public void getItemShouldGenerateNullWhenUnknownValue() {
        Item item = itemFactory.getItem("Milka");
        assertNull(item);
    }
}
