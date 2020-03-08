package ch.epfl.sdp;

public class Shield extends Items {
    private double shieldTime;

    public Shield(GeoPoint location, int itemId, boolean isTaken, double shieldTime) {
        super(location, itemId, isTaken);
        this.shieldTime = shieldTime;
    }
}
