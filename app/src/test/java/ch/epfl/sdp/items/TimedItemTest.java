package ch.epfl.sdp.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.GameThread;
import ch.epfl.sdp.map.MockMap;
import ch.epfl.sdp.utils.JunkCleaner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TimedItemTest {
    private final int countTime = 5;
    private Player user;

    @Before
    public void setup() {
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
            assertTrue(user.status.isPhantom());
            phantom.update();
        }

        // getRemainingTime is in seconds so we still have some frames
        for(int i = GameThread.FPS; i > 0; --i){
            assertTrue(user.status.isPhantom());
            phantom.update();
        }

        assertFalse(user.status.isPhantom());
    }

    @Test
    public void shieldSetsShieldedWhenUpdated(){
        assertFalse(user.status.isShielded());
        Shield shield = new Shield(countTime);
        shield.useOn(user);

        while (shield.getRemainingTime() > 0){
            assertTrue(user.status.isShielded());
            shield.update();
        }

        // getRemainingTime is in seconds so we still have some frames
        for(int i = GameThread.FPS; i > 0; --i){
            assertTrue(user.status.isShielded());
            shield.update();
        }

        assertFalse(user.status.isShielded());
    }

    @Test
    public void shrinkerChangesAOERadiusBack(){
        double originalRadius = user.getAoeRadius();
        int removeAoeRadius = 1;
        Shrinker shrinker = new Shrinker(countTime, removeAoeRadius);
        shrinker.useOn(user);

        while (shrinker.getRemainingTime() > 0){
            assertEquals(user.getAoeRadius(), originalRadius - removeAoeRadius, 0.01);
            shrinker.update();
        }

        // getRemainingTime is in seconds so we still have some frames
        for(int i = GameThread.FPS; i > 0; --i){
            assertEquals(user.getAoeRadius(), originalRadius - removeAoeRadius, 0.01);
            shrinker.update();
        }

        assertEquals(user.getAoeRadius(), originalRadius, 0.01);
    }
}
