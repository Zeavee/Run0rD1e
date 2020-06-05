package ch.epfl.sdp.item;

import java.util.Locale;

import ch.epfl.sdp.entity.Player;

/**
 * This item permits to be invisible from the enemies' and players' points of view
 */
public class Phantom extends TimedItem {

    /**
     * Creates a phantom item
     *
     * @param phantomTime the duration of the invisibility
     */
    public Phantom(int phantomTime) {
        super(String.format(Locale.ENGLISH, "Phantom %d", phantomTime), String.format(Locale.ENGLISH, "Item that hides the presence of the user from other players and the enemies for %d seconds", phantomTime), phantomTime);
    }

    @Override
    public Item clone() {
        return new Phantom(countTime);
    }

    @Override
    public void useOn(Player player) {
        super.useOn(player);
        player.status.setPhantom(true, player);
    }

    @Override
    public double getValue() {
        return countTime;
    }

    @Override
    public void stopUsingOn(Player player) {
        player.status.setPhantom(false, player);
    }
}
