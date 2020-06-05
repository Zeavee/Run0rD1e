package ch.epfl.sdp.items;

import androidx.core.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.ui.MapsActivity;
import ch.epfl.sdp.map.location.GeoPoint;
import ch.epfl.sdp.map.display.Displayable;
import ch.epfl.sdp.map.display.MapApi;
import ch.epfl.sdp.utils.RandomGenerator;

/**
 * A Market is a place where the players can go and buy items using their money
 */
public class Market implements Displayable {
    private final Map<Item, Pair<Integer, Integer>> stock;
    private final GeoPoint loc;
    private boolean hasVisitedMarket = false, isDisplayed = false;


    /**
     * This is a constructor for Market which randomly initialize the items that will be available
     */
    public Market(GeoPoint loc) {
        this.loc = loc;
        stock = new HashMap<>();
        Random random = new Random();
        RandomGenerator randomGenerator = new RandomGenerator();
        for (Item item : randomGenerator.randomItemsList()) {
            double MAX_PRICE = 300;
            double MIN_PRICE = 200;
            stock.put(item, new Pair<>(1 + random.nextInt(5), (int) (Math.round(MIN_PRICE + (MAX_PRICE - MIN_PRICE) * random.nextDouble()))));
        }
    }

    /**
     * A method to get the stock of the Market
     *
     * @return an unmodifiable map that show the items we can buy, their quantity and their price
     */
    public Map<Item, Pair<Integer, Integer>> getStock() {
        return new HashMap<>(stock);
    }

    /**
     * A method to buy an item from the Market
     *
     * @param itemType The type of item the player wants to buy. If the item is not in the stock, we return false
     * @param player   The player that wants to buy the item
     * @return a boolean that shows if the transaction occurred correctly
     */
    public boolean buy(Class<? extends Item> itemType, Player player) {
        Item item = getItemOfType(itemType);
        if (item == null) return false;

        int currentStock = stock.get(item).first;
        int price = stock.get(item).second;

        if (currentStock <= 0 || player.wallet.getMoney(player) < price || !player.wallet.removeMoney(price, player)) {
            return false;
        }
        stock.put(item, new Pair<>(currentStock - 1, price));
        player.getInventory().addItem(item.clone().getName());
        return true;
    }

    private Item getItemOfType(Class<? extends Item> itemType) {
        Item item = null;
        for (Item i : stock.keySet()) {
            if (i.getClass().equals(itemType)) {
                item = i;
            }
        }
        return item;
    }

    /**
     * Method for getting the location for displaying on the map
     *
     * @return a GeoPoint which is a location
     */
    @Override
    public GeoPoint getLocation() {
        return loc;
    }

    private void displayIcon(MapApi mapApi) {
        if (!isDisplayed) {
            mapApi.displaySmallIcon(this, "Market", R.drawable.market);
            isDisplayed = true;
        }
    }

    /**
     * Method for displaying the displayable on the map
     *
     * @param mapApi the API we can use to display the displayable on the map
     */
    @Override
    public void displayOn(MapApi mapApi) {
        displayIcon(mapApi);
        // in meters
        int THRESH_DIST = 50;
        if (PlayerManager.getInstance().getCurrentUser().getLocation().distanceTo(this.getLocation()) <= THRESH_DIST && !hasVisitedMarket) {
            hasVisitedMarket = true;
            ((MapsActivity) (Game.getInstance().getRenderer())).startMarket(this);
        } else if (PlayerManager.getInstance().getCurrentUser().getLocation().distanceTo(this.getLocation()) > THRESH_DIST) {
            hasVisitedMarket = false;
        }
    }
}

