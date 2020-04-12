package ch.epfl.sdp.item;

import android.util.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.logic.RandomGenerator;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

public class Market extends InteractiveEntity implements Displayable {
    private Map<Item, Pair<Integer, Integer>> stock;
    private final double MIN_PRICE = 200;
    private final double MAX_PRICE = 300;
    private final RandomGenerator randomGenerator = new RandomGenerator();

    public Market() {
        super(EntityType.MARKET, RandomGenerator.randomLocationOnCircle(MapsActivity.mapApi.getCurrentLocation(), 1000), true);
        stock = new HashMap<>();
        Random random = new Random();
        for (Item item : randomGenerator.randomItemsList()) {
            stock.put(item, new Pair<>(random.nextInt(5), (int) (Math.round(MIN_PRICE + (MAX_PRICE - MIN_PRICE) * random.nextDouble()))));
        }
    }

    public Map<Item, Pair<Integer, Integer>> getStock() {
        return Collections.unmodifiableMap(stock);
    }

    public boolean buy(Item item, Player player) {
        int currentStock = stock.get(item).first;
        int price = stock.get(item).second;
        if (currentStock <= 0 || player.getMoney() < price || !player.removeMoney(price)) {
            return false;
        } else {
            stock.put(item, new Pair<>(currentStock - 1, price));
            player.getInventory().addItem(item.createCopy());
        }
        return true;
    }

    @Override
    public GeoPoint getLocation() {
        return super.getLocation();
    }

    @Override
    public EntityType getEntityType() {
        return super.getEntityType();
    }

    @Override
    public boolean once() {
        return super.once();
    }
}
