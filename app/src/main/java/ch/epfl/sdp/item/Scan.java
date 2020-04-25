package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;

public class Scan extends TimedItem {
    public Scan(int scanTime) {
        super(String.format("Scan (%d)", scanTime), String.format("Item that scans the entire map and reveals other players for %d seconds", scanTime), scanTime);
    }

    @Override
    public void use() {
        super.use();

        for (Player p : PlayerManager.getPlayers()) {
            Game.getInstance().getMapApi().displayEntity(p);
        }
    }

    @Override
    public void stopUsing() {
        for (Player p : PlayerManager.getPlayers()) {
            Game.getInstance().getMapApi().unDisplayEntity(p);
        }
    }
}
