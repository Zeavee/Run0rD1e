package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;

public class Scan extends TimedItem {
    private int scanTime;

    public Scan(int scanTime) {
        super(String.format("Scan %d", scanTime), String.format("Item that scans the entire map and reveals other players for %d seconds", scanTime), scanTime);
        this.scanTime = scanTime;
    }

    @Override
    public Item clone() {
        return new Scan(scanTime);
    }

    @Override
    public void useOn(Player player) {
        super.useOn(player);

        for (Player p : PlayerManager.getInstance().getPlayers()) {
            p.displayOn(Game.getInstance().getMapApi());
        }
    }

    /**
     * gets the value of the item
     */
    @Override
    public double getValue() {
        return scanTime;
    }

    public void stopUsingOn(Player player) {
        for (Player p : PlayerManager.getInstance().getPlayers()) {
            p.unDisplayOn(Game.getInstance().getMapApi());
        }
    }

}
