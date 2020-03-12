package ch.epfl.sdp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Scan extends Item {
    private double scanTime;

    public Scan(GeoPoint location, boolean isTaken, double scanTime) {
        super(location, 4, isTaken, "Scans the map and shows the location of other players");
        this.scanTime = scanTime;
    }


/*    public void showPlayersLocation(GeoPoint loc){
                *//*
         for(Player player : map) {
            if(player.getPosition().distance(location) < SCAN_RADIUS) {
                player.showOnMap();
            }
         *//*
    } */

    public void showPlayersLocation(ArrayList<Player> allPlayers, MapApi map) {
        if (scanTime > 0) {
            for (Player p: allPlayers) {
                if(p.isPhantom() == false) {
                    map.displayObject(p);
                }
            }
            scanTime = scanTime - 10;
        }
    }

}
