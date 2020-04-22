package ch.epfl.sdp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.DetectableEntity;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DetectableEntityTest {
    Game game;
    MockMapApi mapApi;

    @Before
    public void setup() {
        mapApi = new MockMapApi();
        MapsActivity.setMapApi(mapApi);
        PlayerManager.setUser(new Player());
        mapApi.setCurrentLocation(new GeoPoint(0, 0));
        game = new Game();
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
            public void react(Player player) {
            }
        };

        Game.addToUpdateList(detectableEntity);
        Game.addToDisplayList(detectableEntity);
        assertTrue(Game.updatablesContains(detectableEntity));
        game.update();
        assertFalse(Game.updatablesContains(detectableEntity));
    }
}
