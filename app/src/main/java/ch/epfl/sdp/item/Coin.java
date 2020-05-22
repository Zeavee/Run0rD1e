package ch.epfl.sdp.item;

import java.util.ArrayList;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.utils.RandomGenerator;

/**
 * Class representing a coin in the game
 */
public class Coin extends Item implements Displayable, Updatable {

    private int value;
    private GeoPoint location;
    private boolean isDisplayed;
    private boolean taken;
    private final int coinAoeRadius = 50;

    public Coin(int value, GeoPoint location) {
        super(String.format("Coin of value %d", value), "Medium of exchange that allows a player to buy items in shops");
        this.value = value;
        this.location = location;
        this.taken = false;
        this.isDisplayed = false;
    }

    @Override
    public void useOn(Player player) {
        player.addMoney(value);
    }

    public Item clone() {
        return new Coin(value, this.location);
    }

    public double getValue() {
        return value;
    }

    @Override
    public void update() {
        if (!taken) {
            Player p = PlayerManager.getInstance().getCurrentUser();
            if (this.location.distanceTo(p.getLocation()) - p.getAoeRadius() - coinAoeRadius >= 1) {
                return;
            }
            this.taken = true;
            useOn(p);
            Game.getInstance().removeCurrentFromUpdateList();
            Game.getInstance().removeFromDisplayList(this);
        }
    }

    @Override
    public GeoPoint getLocation() {
        return this.location;
    }

    @Override
    public void displayOn(MapApi mapApi) {
        if (!isDisplayed) {
            String title = String.format("Coin of value %d", value);
            mapApi.displayMarkerCircle(this, 0xFFD700, title, coinAoeRadius);
            this.isDisplayed = true;
        }
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public boolean isTaken() {
        return this.taken;
    }

    /**
     * Method which generates Coins around a given location
     * @param location GeoPoint around which coins will be generated
     * @param amount nb of coins we need to generate
     * @return ArrayList of randomly generated coins around location
     */
    public static ArrayList<Coin> generateCoinsAroundLocation(GeoPoint location, int amount) {
        ArrayList<Coin> generatedCoins = new ArrayList<Coin>();
        RandomGenerator randGen = new RandomGenerator();
        for (int k = 1; k <= amount; k++) {
            generatedCoins.add(randGen.randomCoin(randGen.randomGeoPointAroundLocation(location)));
        }
        return generatedCoins;
    }
}
