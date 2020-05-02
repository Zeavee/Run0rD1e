package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.EntityType;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.map.MapsActivity;

public class Scan extends TimedItem {
    public Scan(int scanTime) {
        super(String.format("Scan (%d)", scanTime), String.format("Item that scans the entire map and reveals other players for %d seconds", scanTime), scanTime);
    }

    @Override
    public void useOn(Player player) {
        super.useOn(player);

        for (Player p : PlayerManager.getInstance().getPlayers()) {
            MapsActivity.mapApi.displayEntity(p);
        }
    }

    @Override
    public void stopUsingOn(Player player) {
        for (Player p : PlayerManager.getInstance().getPlayers()) {
            MapsActivity.mapApi.unDisplayEntity(p);
        }
    }

    public EntityType getEntityType() {
        return EntityType.SCAN;
    }
}
