package ch.epfl.sdp.items;


import androidx.core.util.Pair;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.market.Market;
import ch.epfl.sdp.item.Trap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MarketTest {
    // IMPORTANT: state of PlayerManager must be restored after test teardown
    private Player originalPlayer = PlayerManager.getCurrentUser();
    private Player buyer;
    private Market market;

    @Before
    public void setUp() {
        buyer = new Player(20.0, 20.0, 20, "Skyris", "skyris@gmail.com");
        buyer.addMoney(4000);
        PlayerManager.setCurrentUser(buyer);
        market = new Market(new GeoPoint(3.0, 5.0));
    }

    @Test
    public void constructorSetsUpProductStock(){
        assertNotNull(market.getStock());
    }

    @Test
    public void marketStockReducedUponPurchase(){
        Set<Item> items = market.getStock().keySet();
        Map<Item, Integer> amounts = new HashMap<>();
        for (Map.Entry<Item, Pair<Integer, Integer>> qty : market.getStock().entrySet()) {
            amounts.put(qty.getKey(), qty.getValue().first);
        }
        for (Item i : items){
            market.buy(i.getClass(), buyer);
        }
        Map<Item, Integer> amountsAfter = new HashMap<>();
        for (Map.Entry<Item, Pair<Integer, Integer>> qty : market.getStock().entrySet()) {
            assertTrue(amounts.get(
                   qty.getKey())  == 0 ||
                    (amounts.get(qty.getKey())  == qty.getValue().first+1));
        }
    }

    @Test
    public void marketCanBeExhausted(){
        buyer.addMoney(9000);
        boolean isExhausted=false;
        Set<Item> items = market.getStock().keySet();
        for (int i= 0; i < 10;++i){
            for (Item j : items){
                isExhausted = isExhausted || (!market.buy(j.getClass(), buyer));
            }
        }
        assertTrue(isExhausted);
    }

    @Test
    public void playerCannotBuyNonExistentItem(){
        Item b = new Trap(50, 20);
        assertFalse(market.buy(b.getClass(), buyer));
    }

    @Test
    public void marketDisplayedForever(){
        assertFalse(market.isOnce());
    }

    @After
    public void tearDown(){
        PlayerManager.setCurrentUser(originalPlayer);
    }
}
