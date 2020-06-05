package ch.epfl.sdp.item;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapApi;

/**
 * Class representing a coin in the game
 */
public class Coin extends Item implements Displayable, Updatable {

    private int value;
    private GeoPoint location;
    private boolean isDisplayed;
    private boolean taken;
    private final double aoeRadius = 25.5;

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
            if (this.location.distanceTo(p.getLocation()) - p.getAoeRadius() - aoeRadius >= 0) {
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
            mapApi.displaySmallIcon(this, title, R.drawable.coins);
            this.isDisplayed = true;
        }
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public boolean isTaken() {
        return this.taken;
    }
}
