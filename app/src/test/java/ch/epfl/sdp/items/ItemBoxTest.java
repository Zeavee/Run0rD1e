package ch.epfl.sdp.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.MockMap;
import ch.epfl.sdp.utils.RandomGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemBoxTest {
    private GeoPoint location;
    private MockMap mockMap;

    private final Item dummyItem = new Item("healthpack", "increase healthPoint") {
        @Override
        public Item clone() {
            return null;
        }

        @Override
        public void useOn(Player player) {
        }

        @Override
        public double getValue(){
            return 0.0;
        }
    };

    @Before
    public void setup() {
        RandomGenerator r = new RandomGenerator();
        PlayerManager.getInstance().clear(); // Just to be sure that there are no players
        location = r.randomGeoPoint();
        Player player = new Player("test", "test@gmail.com");
        player.setLocation(location);
        PlayerManager.getInstance().setCurrentUser(player);
        mockMap = new MockMap();
        Game.getInstance().setMapApi(mockMap);
        Game.getInstance().setRenderer(mockMap);
        Game.getInstance().clearGame();
    }

    @After
    public void teardown(){
        PlayerManager.getInstance().clear();
        Game.getInstance().destroyGame();
    }

    @Test
    public void takingItemBoxMakesItDisappear(){
        ItemBox itemBox = new ItemBox(location);

        Game.getInstance().addToUpdateList(itemBox);
        Game.getInstance().addToDisplayList(itemBox);

        assertTrue(Game.getInstance().updatablesContains(itemBox));
        assertTrue(Game.getInstance().displayablesContains(itemBox));
        assertFalse(itemBox.isTaken());

        Game.getInstance().update();
        assertTrue(itemBox.isTaken());
    }

    @Test
    public void takingItemBoxAddItemsToInventory() {
        Item item = new Item("healthpack", "increase healthPoint") {

            @Override
            public Item clone() {
                return null;
            }

            @Override
            public void useOn(Player player) {
            }

            @Override
            public double getValue() {
                return 0.0;
            }
        };


        assertFalse(PlayerManager.getInstance().getCurrentUser().getInventory().getItems().containsKey(item.getName()));

        for (int i = 0; i < 2; ++i) {
            ItemBox itemBox = new ItemBox(location);
            itemBox.putItems(item, 1);

            Game.getInstance().addToUpdateList(itemBox);
            Game.getInstance().addToDisplayList(itemBox);

            Game.getInstance().update();

            assertTrue(PlayerManager.getInstance().getCurrentUser().getInventory().getItems().containsKey(item.getName()));
        }

        assertEquals(2, (int) PlayerManager.getInstance().getCurrentUser().getInventory().getItems().get(item.getName()));

    }
    @Test
    public void isTakenShouldWork() {
        ItemBox itemBox = new ItemBox(location);

        Game.getInstance().addToUpdateList(itemBox);
        Game.getInstance().addToDisplayList(itemBox);

        assertTrue(Game.getInstance().updatablesContains(itemBox));
        assertTrue(Game.getInstance().displayablesContains(itemBox));
        assertFalse(itemBox.isTaken());
    }

    @Test
    public void putItemsShouldWork() {
        ItemBox itemBox = new ItemBox(location);

        itemBox.putItems(dummyItem, 2);
        Player player = new Player("a", "b");
        itemBox.react(player);
        assertEquals(2, player.getInventory().size());
    }

    @Test
    public void displayOnWorks() {
        ItemBox itemBox = new ItemBox(location);
        itemBox.displayOn(mockMap);
        assertEquals(1, mockMap.getDisplayables().size());
        itemBox.displayOn(mockMap);
        mockMap.getDisplayables().remove(itemBox);
        itemBox.displayOn(mockMap);
        assertEquals(0, mockMap.getDisplayables().size());
        itemBox.setReDisplay(true);
        itemBox.displayOn(mockMap);
        assertEquals(1, mockMap.getDisplayables().size());
    }
}
