package ch.epfl.sdp.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.utils.JunkCleaner;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.GameThread;
import ch.epfl.sdp.item.Phantom;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;
import ch.epfl.sdp.item.TimedItem;
import ch.epfl.sdp.map.MockMap;
import ch.epfl.sdp.utils.RandomGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TimedItemTest {
    public final int countTime = 5;
    Player user;

    @Before
    public void setup() {
        RandomGenerator r = new RandomGenerator();
        Game.getInstance().setMapApi(new MockMap());
        Player player = new Player("test name", "test@email.com");
        PlayerManager.getInstance().setCurrentUser(player);
        user = PlayerManager.getInstance().getCurrentUser();
    }

    @After
    public void teardown(){
        JunkCleaner.clearAll();
    }

    @Test
    public void timedItemCounterIsDecrementedBy1WhenUpdated(){
        TimedItem timedItem = new Shield(countTime) {};

        for(int i = countTime*GameThread.FPS; i > 0; --i) {
            assertEquals(timedItem.getRemainingTime(), i/ GameThread.FPS);
            System.out.println(Math.floorDiv(i,GameThread.FPS) + " i =  " + i  +  " " +  timedItem.getRemainingTime());
            timedItem.update();
            assertEquals(timedItem.getRemainingTime(), (i-1)/GameThread.FPS);
        }
    }

    @Test
    public void timedItemIsDeletedFromTheListWhenTimeIsFinished(){
        TimedItem timedItem = new Shield(countTime) {};
        Game.getInstance().addToUpdateList(timedItem);

        for(int i = countTime*GameThread.FPS; i > 0; --i) {
            timedItem.update();
        }

//        timedItem.update();
        //assertFalse(Game.getInstance().updatablesContains(timedItem));
    }

    @Test
    public void phantomGetsUpdated(){
        MockMap map = new MockMap();
        Game.getInstance().setMapApi(map);
        Game.getInstance().setRenderer(map);
        Game.getInstance().initGame();
        PlayerManager.getInstance().addPlayer(user);
        PlayerManager.getInstance().addPlayer(new Player("test","test"));
        Phantom phantom = new Phantom(countTime);
        phantom.useOn(user);

        while(Game.getInstance().getDisplayables().isEmpty()){}

        while (phantom.getRemainingTime() > 0){
            assertFalse(Game.getInstance().getDisplayables().isEmpty());
            phantom.update();
        }

        // getRemainingTime is in seconds so we still have some frames
        for(int i = GameThread.FPS; i > 0; --i){
            assertFalse(Game.getInstance().getDisplayables().isEmpty());
            phantom.update();
        }

        assertTrue(Game.getInstance().getDisplayables().isEmpty());
    }

    @Test
    public void shieldSetsShieldedWhenUpdated(){
        assertFalse(user.isShielded());
        Shield shield = new Shield(countTime);
        shield.useOn(user);

        while (shield.getRemainingTime() > 0){
            assertTrue(user.isShielded());
            shield.update();
        }

        // getRemainingTime is in seconds so we still have some frames
        for(int i = GameThread.FPS; i > 0; --i){
            assertTrue(user.isShielded());
            shield.update();
        }

        assertFalse(user.isShielded());
    }

    @Test
    public void shrinkerChangesAOERadiusBack(){
        Double originalRadius = user.getAoeRadius();
        int removeAoeRadius = 1;
        Shrinker shrinker = new Shrinker(countTime, removeAoeRadius);
        shrinker.useOn(user);

        while (shrinker.getRemainingTime() > 0){
            assertTrue(user.getAoeRadius() == originalRadius - removeAoeRadius);
            shrinker.update();
        }

        // getRemainingTime is in seconds so we still have some frames
        for(int i = GameThread.FPS; i > 0; --i){
            assertTrue(user.getAoeRadius() == originalRadius - removeAoeRadius);
            shrinker.update();
        }

        assertTrue(user.getAoeRadius() == originalRadius);
    }
}
