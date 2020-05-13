package ch.epfl.sdp.item;


import androidx.core.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.utils.RandomGenerator;

/**
 * A Market is a place where the players can go and buy items using their money
 */
public class Market implements Displayable {
    private Map<Item, Pair<Integer, Integer>> stock;
    private final double MIN_PRICE = 200;
    private final double MAX_PRICE = 300;
    private GeoPoint location;
    private final RandomGenerator randomGenerator = new RandomGenerator();

    /**
     * This is a constructor for Market which randomly initialize the items that will be available
     */
    public Market() {
        this.location = new RandomGenerator().randomLocationOnCircle(PlayerManager.getInstance().getCurrentUser().getLocation(), 1000);
        stock = new HashMap<>();
        Random random = new Random();
        for (Item item: randomGenerator.randomItemsList()) {
            stock.put(item, new Pair<>(random.nextInt(5), (int) (Math.round(MIN_PRICE + (MAX_PRICE - MIN_PRICE) * random.nextDouble()))));
        }
    }

    /**
     * A method to get the stock of the Market
     * @return an unmodifiable map that show the items we can buy, their quantity and their price
     */
    public Map<Item, Pair<Integer, Integer>> getStock() {
        return new HashMap<>(stock);
    }

    /**
     * A method to buy an item from the Market
     * @param item The item the player wants to buy. If the item is not in the stock, we return false
     * @param player The player that wants to buy the item
     * @return a boolean that shows if the transaction occurred correctly
     */
    public boolean buy(Item item, Player player) {
        if (!stock.containsKey(item)) {
            return false;
        }
        int currentStock =stock.get(item).first;
        int price = stock.get(item).second;
        if (currentStock <= 0 || player.getMoney() < price || !player.removeMoney(price)) {
            return false;
        } else {
            stock.put(item, new Pair<>(currentStock-1, price));
            player.getInventory().addItem(item.clone().getName());
        }
        return true;
    }

    @Override
    public GeoPoint getLocation() {
        return location;
    }

    /**
     * Method for displaying the displayable on the map
     *
     * @param mapApi the API we can use to display the displayable on the map
     */
    @Override
    public void displayOn(MapApi mapApi) {
        // TODO: when the user moves onto the displayed icon of this market then switch to the market activity
    }
}

