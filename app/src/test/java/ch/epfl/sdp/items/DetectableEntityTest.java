package ch.epfl.sdp.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.map.MockMapApi;
import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.DetectableEntity;
import ch.epfl.sdp.map.MapsActivity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DetectableEntityTest {
    MockMapApi mapApi;

    @Before
    public void setup() {
        mapApi = new MockMapApi();
        Game.getInstance().setMapApi(mapApi);
        PlayerManager.setCurrentUser(new Player("",""));
        mapApi.setCurrentLocation(new GeoPoint(0, 0));
    }

    @After
    public void teardown() {
        PlayerManager.removeAll();
    }

    @Test
    public void detectableEntityGetsRemovedAfter1ReactionIfOnceIsTrue() {
        GeoPoint itemLocation = new GeoPoint(0, 0);
        DetectableEntity detectableEntity = new DetectableEntity(EntityType.NONE, itemLocation, true) {
            @Override
            public void displayOn(MapApi mapApi) {

            }

            @Override
            public void react(Player player) {
            }
        };

        Game.getInstance().addToUpdateList(detectableEntity);
        Game.getInstance().addToDisplayList(detectableEntity);
        assertTrue(Game.getInstance().updatablesContains(detectableEntity));
        Game.getInstance().update();
        assertFalse(Game.getInstance().updatablesContains(detectableEntity));
    }
}
