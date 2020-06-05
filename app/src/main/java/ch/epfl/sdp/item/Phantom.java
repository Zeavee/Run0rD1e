package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.Player;

public class Phantom extends TimedItem {
    private final int phantomTime;

    public Phantom(int phantomTime) {
        super(String.format("Phantom %d", phantomTime), String.format("Item that hides the presence of the user from other players and the enemies for %d seconds", phantomTime), phantomTime);
        this.phantomTime = phantomTime;
    }

    @Override
    public Item clone() {
        return new Phantom(phantomTime);
    }

    @Override
    public void useOn(Player player) {
        super.useOn(player);
        player.setPhantom(true);
    }

    /**
     * gets the value of the item
     */
    @Override
    public double getValue() {
        return phantomTime;
    }

    public void stopUsingOn(Player player) {
        player.setPhantom(false);
    }
}
