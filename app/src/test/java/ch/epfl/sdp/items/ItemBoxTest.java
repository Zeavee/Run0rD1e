package ch.epfl.sdp.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.map.MockMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemBoxTest {
    private Player player;
    private GeoPoint location;
    private MockMap mockMap;

    @Before
    public void setup() {
        PlayerManager.getInstance().clear(); // Just to be sure that there are no players
        location = new GeoPoint(0,0);
        player = new Player("test","test@gmail.com");
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

        assertTrue(PlayerManager.getInstance().getCurrentUser().getInventory().getItems().get(item.getName()) == 2);

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


}
