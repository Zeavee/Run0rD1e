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
import ch.epfl.sdp.item.Coin;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.market.Market;
import ch.epfl.sdp.utils.RandomGenerator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MarketTest {
    private Player buyer;
    private Market market;

    @Before
    public void setUp() {
        RandomGenerator r = new RandomGenerator();
        GeoPoint g = r.randomGeoPoint();
        buyer = new Player(g.getLongitude(), g.getLatitude(), 20, "test name", "test@email.com");
        buyer.wallet.addMoney(4000, buyer);
        PlayerManager.getInstance().setCurrentUser(buyer);
        market = new Market(new GeoPoint(1.0, 3.4));
    }

    @After
    public void tearDown(){
        PlayerManager.getInstance().clear();
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
        for (Map.Entry<Item, Pair<Integer, Integer>> qty : market.getStock().entrySet()) {
            assertTrue(amounts.get(
                   qty.getKey())  == 0 ||
                    (amounts.get(qty.getKey())  == qty.getValue().first+1));
        }
    }

    @Test
    public void marketCanBeExhausted(){
        buyer.wallet.addMoney(9000, buyer);
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
        Item b = new Coin(10, new GeoPoint(3.0,1.0));
        assertFalse(market.buy(b.getClass(), buyer));
    }
}
