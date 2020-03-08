package ch.epfl.sdp;

public class Scan extends Item {
    private double scanTime;

    public Scan(GeoPoint location, int itemId, boolean isTaken, double scanTime) {
        super(location, itemId, isTaken);
        this.scanTime = scanTime;
    }

    public void showPlayersLocation(GeoPoint loc){
                /*
         for(Player player : map) {
            if(player.getPosition().distance(location) < SCAN_RADIUS) {
                player.showOnMap();
            }
         */
    }

}
