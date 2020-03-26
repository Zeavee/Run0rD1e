package ch.epfl.sdp;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Scan extends Item {
    private double scanTime;
    private MapApi map;

    public Scan(GeoPoint location, boolean isTaken, double scanTime, MapApi map) {
        super(location, "Scan", isTaken, "Item that scans the entire map and reveals other players for a short delay");
        this.scanTime = scanTime;
        this.map = map;
    }

    public void showAllPlayers(ArrayList<Player> players) {
        for (Player p : players) {
            map.displayEntity(p);
        }
        TimerTask turnOffScaner = new TimerTask() {
            @Override
            public void run() {
                for(Player p: players) {
                    map.unDisplayEntity(p);
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(turnOffScaner, (long) scanTime * 1000);
    }
}
