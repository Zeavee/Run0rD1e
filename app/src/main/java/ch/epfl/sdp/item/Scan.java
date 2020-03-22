package ch.epfl.sdp.item;

import ch.epfl.sdp.map.GeoPoint;

public class Scan extends Item {
    private double scanTime;

    public Scan(GeoPoint location, boolean isTaken, double scanTime) {
        super(location, "Scan", isTaken, "Item that scans the entire map and reveals other players for a short delay");
        this.scanTime = scanTime;
    }
}
