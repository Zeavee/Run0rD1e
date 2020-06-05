package ch.epfl.sdp.items;

import java.util.Locale;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.map.location.GeoPoint;
import ch.epfl.sdp.map.display.Displayable;
import ch.epfl.sdp.map.display.MapApi;

/**
 * Class representing a coin in the game
 */
public class Coin extends Item implements Displayable, Updatable {

    private final int value;
    private GeoPoint location;
    private boolean isDisplayed;
    private boolean taken;

    public Coin(int value, GeoPoint location) {
        super(String.format(Locale.ENGLISH, "Coin of value %d", value), "Medium of exchange that allows a player to buy items in shops");
        this.value = value;
        this.location = location;
        this.taken = false;
        this.isDisplayed = false;
    }

    @Override
    public void useOn(Player player) {
        player.wallet.addMoney(value, player);
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
            double aoeRadius = 25.5;
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
            String title = String.format(Locale.ENGLISH, "Coin of value %d", value);
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
