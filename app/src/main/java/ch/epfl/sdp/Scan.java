package ch.epfl.sdp;
import java.util.ArrayList;

public class Scan extends Item {
    private double scanTime;

    public Scan(GeoPoint location, boolean isTaken, double scanTime) {
        super(location, "Scan", isTaken, "Item that scans the entire map and reveals other players");
        this.scanTime = scanTime;
    }

    public void showPlayersLocation(ArrayList<Player> allPlayers, MapApi map) {
        //TODO
    }

}
