package ch.epfl.sdp.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Coin;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Phantom;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;
import ch.epfl.sdp.map.MockMap;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ItemsTest {
    private Healthpack healthpack;
    private int healthAmount;
    private Shield shield;
    private int shieldTime;
    private Shrinker shrinker;
    private Phantom phantom;
    private int phantomTime;

    @Before
    public void setup(){
        Game.getInstance().setMapApi(new MockMap());
        Player player = new Player("Test Name", "test@email.com");
        PlayerManager.getInstance().addPlayer(player);
        PlayerManager.getInstance().setCurrentUser(player);

        shieldTime = 40;
        healthAmount = 60;
        phantomTime = 50;

        healthpack = new Healthpack(healthAmount);
        shield = new Shield(shieldTime);
        shrinker = new Shrinker( 40, 10);
        phantom = new Phantom(phantomTime);
    }

    @After
    public void teardown(){
        PlayerManager.getInstance().clear();
        Game.getInstance().destroyGame();
    }

    @Test
    public void healthpackTest() {
        Healthpack healthpack = new Healthpack(1);

        PlayerManager.getInstance().getCurrentUser().status.setHealthPoints(10, PlayerManager.getInstance().getCurrentUser());
        healthpack.useOn(PlayerManager.getInstance().getCurrentUser());
        assertEquals(11, PlayerManager.getInstance().getCurrentUser().status.getHealthPoints(), 0.01);
    }

    @Test
    public void ifItemNotInInventoryNothingHappens() {
        Player player = new Player(6.149290, 46.212470, 50,
                "Skyris", "test@email.com", false); //player position is in Geneva
        player.getInventory().removeItem(new Healthpack(0).getName());
        assertEquals(0, player.getInventory().getItems().size());
    }

    @Test
    public void phantomTest() {
        assertEquals(phantomTime, phantom.getValue(), 0.0);
    }


    @Test
    public void shieldTest() {
        assertEquals(40, shield.getRemainingTime(), 0);
        assertEquals(shieldTime, shield.getValue(), 0.0);
    }

    @Test
    public void shrinkerTest() {
        assertEquals(40, shrinker.getRemainingTime(), 0);
        assertEquals(10, shrinker.getShrinkingRadius(), 0);
        assertEquals(shrinker.getShrinkingRadius(), shrinker.getValue(), 0.0);
    }


    @Test
    public void increaseHealth() {
        PlayerManager.getInstance().getCurrentUser().status.setHealthPoints(30, PlayerManager.getInstance().getCurrentUser());
        PlayerManager.getInstance().setCurrentUser(PlayerManager.getInstance().getCurrentUser());
        healthpack.useOn(PlayerManager.getInstance().getCurrentUser());
        assertEquals(90, PlayerManager.getInstance().getCurrentUser().status.getHealthPoints(), 0);
        healthpack.useOn(PlayerManager.getInstance().getCurrentUser());
        assertEquals(100, PlayerManager.getInstance().getCurrentUser().status.getHealthPoints(), 0);
        assertEquals(healthAmount, healthpack.getValue(), 0.01);
    }

    @Test
    public void coinTest() {
        PlayerManager.getInstance().getCurrentUser().wallet.removeMoney(PlayerManager.getInstance().getCurrentUser().wallet.getMoney(PlayerManager.getInstance().getCurrentUser()), PlayerManager.getInstance().getCurrentUser());
        Coin c = new Coin(5, new GeoPoint(10,10));
        assertEquals(5, c.getValue(), 0.0);
        c.useOn(PlayerManager.getInstance().getCurrentUser());
        assertEquals(5, PlayerManager.getInstance().getCurrentUser().wallet.getMoney(PlayerManager.getInstance().getCurrentUser()));
    }
}