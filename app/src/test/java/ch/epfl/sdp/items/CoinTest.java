package ch.epfl.sdp.items;

import org.junit.Test;

import java.util.ArrayList;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Coin;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.map.MockMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CoinTest {

    private MockMap mockMap;

    @Test
    public void clonedCoinHasSameValue(){
        Coin dime = new Coin(10, new GeoPoint(10,10));
        Item clonedDime = dime.clone();
        assertEquals(dime.getValue(), clonedDime.getValue(), 0.0);
    }

    @Test
    public void userBankIncreasesWhenCoinUsed(){
        Player broke = new Player(20.0, 20.0, 100, "amroa", "amro.abdrabo@gmail.com", false);
        broke.removeMoney(broke.getMoney());
        PlayerManager.getInstance().setCurrentUser(broke);
        Coin dime  = new Coin(10, new GeoPoint(10,10));
        dime.useOn(broke);
        assertEquals(broke.getMoney(), dime.getValue(), 0.0);
    }


    @Test
    public void coinDisappearsWhenPicked() {
        PlayerManager.getInstance().clear(); // Just to be sure that there are no players
        GeoPoint l = new GeoPoint(10, 10);
        Player player = new Player("testPlayer", "testPlayer@gmail.com");
        player.setLocation(l);
        PlayerManager.getInstance().setCurrentUser(player);
        mockMap = new MockMap();
        Game.getInstance().setMapApi(mockMap);
        Game.getInstance().setRenderer(mockMap);
        Game.getInstance().clearGame();
        Coin c = new Coin(10, l);
        Game.getInstance().addToUpdateList(c);
        Game.getInstance().addToDisplayList(c);
        assertTrue(Game.getInstance().updatablesContains(c));
        assertTrue(Game.getInstance().displayablesContains(c));
        assertFalse(c.isTaken());
        Game.getInstance().update();
        assertTrue(c.isTaken());
        PlayerManager.getInstance().clear();
    }

    @Test
    public void getLocationTest() {
        GeoPoint g = new GeoPoint(10,10);
        Coin c = new Coin(10, g);
        assertEquals(c.getLocation(), g);
    }

    @Test
    public void setLocationTest() {
        mockMap = new MockMap();
        Coin c = new Coin(10 , new GeoPoint(10, 10));
        GeoPoint g = new GeoPoint(60,60);
        c.displayOn(mockMap);
        c.setLocation(g);
        assertEquals(g, c.getLocation());
    }

}
