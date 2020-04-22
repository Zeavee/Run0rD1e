package ch.epfl.sdp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.map.MapsActivity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemBoxTest {
    Player player;
    GeoPoint location;
    MockMapApi mockMapApi;

    @Before
    public void setup() {
        PlayerManager.removeAll(); // Just to be sure that there are no players
        location = new GeoPoint(0,0);
        player = new Player("","");
        player.setLocation(location);
        PlayerManager.setCurrentUser(player);
        mockMapApi = new MockMapApi();
        MapsActivity.setMapApi(mockMapApi);
        mockMapApi.setCurrentLocation(location);
        Game.getInstance().clearGame();
    }

    @After
    public void teardown(){
        PlayerManager.removeAll();
    }

    @Test
    public void takingItemBoxMakesItDisappear(){
        ItemBox itemBox = new ItemBox();
        itemBox.setLocation(location);

        Game.getInstance().addToUpdateList(itemBox);
        Game.getInstance().addToDisplayList(itemBox);

        assertTrue(Game.getInstance().updatablesContains(itemBox));
        assertFalse(Game.getInstance().displayablesContains(itemBox));
        assertFalse(itemBox.isTaken());

        Game.getInstance().update();

        assertFalse(Game.getInstance().updatablesContains(itemBox));
        assertFalse(Game.getInstance().displayablesContains(itemBox));
        assertTrue(itemBox.isTaken());
    }

    @Test
    public void takingItemBoxAddItemsToInventory() {
        Item item = new Item("", "") {
            @Override
            public EntityType getEntityType() {
                return EntityType.NONE;
            }

            @Override
            public void use() {
            }
        };


        assertFalse(PlayerManager.getCurrentUser().getInventory().getItems().containsKey(item));

        for (int i = 0; i < 2; ++i) {
            ItemBox itemBox = new ItemBox();

            itemBox.setLocation(location);
            itemBox.putItems(item, 1);

            Game.getInstance().addToUpdateList(itemBox);
            Game.getInstance().addToDisplayList(itemBox);

            Game.getInstance().update();

            assertTrue(PlayerManager.getCurrentUser().getInventory().getItems().containsKey(item));
        }

        assertTrue(PlayerManager.getCurrentUser().getInventory().getItems().get(item) == 2);
    }
}
