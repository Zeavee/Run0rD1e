package ch.epfl.sdp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;
import ch.epfl.sdp.item.TimedItem;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.map.MapsActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TimedItemTest {
    public final int FPS = 30;
    public final int countTime = 2;
    Player user;

    @Before
    public void setup() {
        MapsActivity.setMapApi(new MockMapApi());
        Player player = new Player();
        PlayerManager.setUser(player);
        user = PlayerManager.getUser();
    }

    @After
    public void teardown(){
        PlayerManager.removeAll();
    }

    @Test
    public void timedItemCounterIsDecrementedBy1WhenUpdated(){
        TimedItem timedItem = new Shield(countTime) {};

        for(int i = countTime*FPS; i > 0; --i) {
            assertEquals(timedItem.getRemainingTime(), i/FPS);
            timedItem.update();
            assertEquals(timedItem.getRemainingTime(), (i-1)/FPS);
        }
    }

    @Test
    public void timedItemIsDeletedFromTheListWhenTimeIsFinished(){
        TimedItem timedItem = new Shield(countTime) {};
        Game game = new Game();
        Game.addToUpdateList(timedItem);

        for(int i = countTime*FPS; i > 0; --i) {
            timedItem.update();
        }

        timedItem.update();
        assertFalse(Game.updatablesContains(timedItem));
    }

    @Test
    public void scanGetsUpdated(){
        MockMapApi map = new MockMapApi();
        MapsActivity.setMapApi(map);
        PlayerManager.addPlayer(user);
        Scan scan = new Scan(countTime);
        scan.use();

        while (scan.getRemainingTime() > 0){
            assertFalse(map.getDisplayables().isEmpty());
            scan.update();
        }

        // getRemainingTime is in seconds so we still have some frames
        for(int i = FPS - 1; i > 0; --i){
            assertFalse(map.getDisplayables().isEmpty());
            scan.update();
        }

        assertTrue(map.getDisplayables().isEmpty());
    }

    @Test
    public void shieldSetsShieldedWhenUpdated(){
        assertFalse(user.isShielded());
        Shield shield = new Shield(countTime);
        shield.use();

        while (shield.getRemainingTime() > 0){
            assertTrue(user.isShielded());
            shield.update();
        }

        // getRemainingTime is in seconds so we still have some frames
        for(int i = FPS - 1; i > 0; --i){
            assertTrue(user.isShielded());
            shield.update();
        }

        assertFalse(user.isShielded());
    }

    @Test
    public void shrinkerChangesAOERadiusBack(){
        Double originalRadius = user.getAoeRadius();
        int removeAoeRadius = 10;
        Shrinker shrinker = new Shrinker(countTime, removeAoeRadius);
        shrinker.use();

        while (shrinker.getRemainingTime() > 0){
            assertTrue(user.getAoeRadius() == originalRadius - removeAoeRadius);
            shrinker.update();
        }

        // getRemainingTime is in seconds so we still have some frames
        for(int i = FPS - 1; i > 0; --i){
            assertTrue(user.getAoeRadius() == originalRadius - removeAoeRadius);
            shrinker.update();
        }

        assertTrue(user.getAoeRadius() == originalRadius);
    }
}
