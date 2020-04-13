package ch.epfl.sdp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemBoxTest {
    Player player;
    GeoPoint location;
    MockMapApi mockMapApi;
    Game game;

    @Before
    public void setup() {
        PlayerManager.removeAll(); // Just to be sure that there are no players
        location = new GeoPoint(0,0);
        player = new Player();
        player.setLocation(location);
        PlayerManager.setUser(player);
        mockMapApi = new MockMapApi();
        MapsActivity.setMapApi(mockMapApi);
        mockMapApi.setCurrentLocation(location);
        game = new Game();
    }

    @After
    public void teardown(){
        PlayerManager.removeAll();
    }

    @Test
    public void takingItemBoxMakesItDisappear(){
        ItemBox itemBox = new ItemBox();
        itemBox.setLocation(location);

        Game.addToUpdateList(itemBox);
        Game.addToDisplayList(itemBox);

        assertTrue(Game.updatablesContains(itemBox));
        assertTrue(Game.displayablesContains(itemBox));
        assertFalse(itemBox.isTaken());

        game.update();

        assertFalse(Game.updatablesContains(itemBox));
        assertFalse(Game.displayablesContains(itemBox));
        assertTrue(itemBox.isTaken());
    }

    @Test
    public void takingItemBoxAddItemsToInventory() {
        Item item = new Item("", "") {
            @Override
            public Item createCopy() {
                return null;
            }

            @Override
            public void use() {
            }
        };


        assertFalse(PlayerManager.getUser().getInventory().getItems().containsKey(item));

        for (int i = 0; i < 2; ++i) {
            ItemBox itemBox = new ItemBox();

            itemBox.setLocation(location);
            itemBox.putItems(item, 1);

            Game.addToUpdateList(itemBox);
            Game.addToDisplayList(itemBox);

            game.update();

            assertTrue(PlayerManager.getUser().getInventory().getItems().containsKey(item));
        }

        assertTrue(PlayerManager.getUser().getInventory().getItems().get(item) == 2);
    }
}
