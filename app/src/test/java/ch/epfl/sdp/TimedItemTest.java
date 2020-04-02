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
    Player player;

    @Before
    public void setup(){
        player = new Player();
    }

    @After
    public void teardown(){
        PlayerManager.removeAll();
    }

    @Test
    public void timedItemCounterIsDecrementedBy1WhenUpdated(){
        TimedItem timedItem = new TimedItem(null, null, false, null, countTime) {};

        for(int i = countTime*FPS; i > 0; --i) {
            assertEquals(timedItem.getRemainingTime(), i/FPS);
            timedItem.update();
            assertEquals(timedItem.getRemainingTime(), (i-1)/FPS);
        }
    }

    @Test
    public void timedItemIsDeletedFromTheListWhenTimeIsFinished(){
        TimedItem timedItem = new TimedItem(null, null, false, null, countTime) {};
        Game game = new Game(null);
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
        PlayerManager.addPlayer(player);
        Scan scan = new Scan(null, false, countTime);

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
        assertFalse(player.isShielded());
        Shield shield = new Shield(null, false, countTime, player);

        while (shield.getRemainingTime() > 0){
            assertTrue(player.isShielded());
            shield.update();
        }

        // getRemainingTime is in seconds so we still have some frames
        for(int i = FPS - 1; i > 0; --i){
            assertTrue(player.isShielded());
            shield.update();
        }

        assertFalse(player.isShielded());
    }

    @Test
    public void shrinkerChangesAOERadiusBack(){
        Double originalRadius = player.getAoeRadius();
        int removeAoeRadius = 10;
        Shrinker shrinker = new Shrinker(null, false,countTime, removeAoeRadius, player);

        while (shrinker.getRemainingTime() > 0){
            assertTrue(player.getAoeRadius() == originalRadius - removeAoeRadius);
            shrinker.update();
        }

        // getRemainingTime is in seconds so we still have some frames
        for(int i = FPS - 1; i > 0; --i){
            assertTrue(player.getAoeRadius() == originalRadius - removeAoeRadius);
            shrinker.update();
        }

        assertTrue(player.getAoeRadius() == originalRadius);
    }
}
