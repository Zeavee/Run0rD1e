package ch.epfl.sdp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemBoxTest {
    Player player;
    GeoPoint location;
    MockMapApi mockMapApi;

    @Before
    public void setup() {
        location = new GeoPoint(0,0);
        player = new Player();
        player.setLocation(location);
        PlayerManager.setUser(player);
        mockMapApi = new MockMapApi();
        MapsActivity.setMapApi(mockMapApi);
        Game game = new Game();
    }

    @After
    public void teardown(){
        PlayerManager.removeAll();
    }

    @Test
    public void takingItemBoxMakesItDisappear(){
        mockMapApi.setCurrentLocation(new GeoPoint(0,0));
        ItemBox itemBox = new ItemBox();
        itemBox.setLocation(location);

        itemBox.update();

        assertFalse(Game.updatablesContains(itemBox));
        assertFalse(Game.displayablesContains(itemBox));
    }
}
