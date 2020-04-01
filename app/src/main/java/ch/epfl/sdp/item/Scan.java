package ch.epfl.sdp.item;
import java.util.ArrayList;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.GameThread;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapApi;

public class Scan extends TimedItem {
    private MapApi map;

    public Scan(GeoPoint location, boolean isTaken, int scanTime, MapApi map) {
        super(location, "Scan", isTaken, "Item that scans the entire map and reveals other players for a short delay", scanTime);
        this.map = map;
    }

    public void showAllPlayers() {
        /*Player player1 = new Player(6.149290, 46.212470, 50, "Skyris", "test@email.com");
        Player player2 = new Player(6.56599, 46.52224, 50, "Iris", "test2@email.com");
        Player player3 = new Player(7.44428, 46.94652, 50, "player3", "test3@email.com");
        Player player4 = new Player(9.34324, 47.24942, 50, "player4", "test4@email.com");

        PlayerManager.addPlayer(player1);
        PlayerManager.addPlayer(player2);
        PlayerManager.addPlayer(player3);
        PlayerManager.addPlayer(player4);*/

        for (Player p : PlayerManager.getPlayers()) {
            map.displayEntity(p);
        }
    }

    @Override
    public void update() {
        super.update();
        System.out.println(String.format("Update Scan, count %d", counter));

        if(super.counter == 0) {
            for(Player p: PlayerManager.getPlayers()) {
                map.unDisplayEntity(p);
            }
        }
    }
}