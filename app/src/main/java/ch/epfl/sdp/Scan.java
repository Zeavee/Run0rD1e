package ch.epfl.sdp;

public class Scan extends Items {
    private double scanTime;

    public Scan(GeoPoint location, int itemId, boolean isTaken, double scanTime) {
        super(location, itemId, isTaken);
        this.scanTime = scanTime;
    }

    public void showPlayersLocation(GeoPoint loc){}

}
