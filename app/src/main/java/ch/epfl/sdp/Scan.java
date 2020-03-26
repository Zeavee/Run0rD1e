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

    public void showAllPlayers() {

        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player(6.149290, 46.212470, 50, "Skyris", "test@email.com");
        Player player2 = new Player(6.56599, 46.52224, 50, "Iris", "test2@email.com");
        Player player3 = new Player(7.44428, 46.94652, 50, "player3", "test3@email.com");
        Player player4 = new Player(9.34324, 47.24942, 50, "player4", "test4@email.com");

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        for (Player p : players) {
            map.displayEntity(p);
        }
        //Not undisplaying as of yet
        //Timer timer = new Timer();
        //TimerTask turnOffScaner = new TimerTask() {
            //@Override
            //public void run() {
                //for(Player p: players) {
                    //map.unDisplayEntity(p);
                //}
            //}
        //};

        //timer.schedule(turnOffScaner, (long) scanTime * 1000);
    }
}
