package ch.epfl.sdp.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.item.Item;
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
        TimedItem timedItem = new TimedItem("","", 10) {
            @Override
            public void stopUsingOn(Player player) {

            }

            @Override
            public Item clone() {
                return null;
            }

            @Override
            public double getValue() {
                return 0;
            }
        };
        Game.getInstance().addToUpdateList(timedItem);

        while (timedItem.getRemainingTime() > 0){
            timedItem.update();
        }

        for(int i = countTime*GameThread.FPS; i > 0; --i) {
            timedItem.update();
        }

        assertFalse(Game.getInstance().updatablesContains(timedItem));
    }

    @Test
    public void phantomGetsUpdated(){
        MockMap map = new MockMap();
        Game.getInstance().setMapApi(map);
        Game.getInstance().setRenderer(map);
        Phantom phantom = new Phantom(countTime);
        phantom.useOn(user);

        while (phantom.getRemainingTime() > 0){
            assertTrue(user.isPhantom());
            phantom.update();
        }

        // getRemainingTime is in seconds so we still have some frames
        for(int i = GameThread.FPS; i > 0; --i){
            assertTrue(user.isPhantom());
            phantom.update();
        }

        assertTrue(!user.isPhantom());
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
