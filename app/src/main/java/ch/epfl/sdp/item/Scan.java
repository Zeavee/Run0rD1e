package ch.epfl.sdp.item;
import java.util.ArrayList;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.GameThread;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.map.MapsActivity;

public class Scan extends TimedItem {
    public Scan(GeoPoint location, boolean isTaken, int scanTime) {
        super(location, "Scan", isTaken, "Item that scans the entire map and reveals other players for a short delay", scanTime);
        showAllPlayers();
    }

    public void showAllPlayers() {
        for (Player p : PlayerManager.getPlayers()) {
            MapsActivity.mapApi.displayEntity(p);
        }
    }

    @Override
    public void update() {
        super.update();

        if(super.counter == 0) {
            for(Player p: PlayerManager.getPlayers()) {
                MapsActivity.mapApi.unDisplayEntity(p);
            }
        }
    }
}