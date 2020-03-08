package ch.epfl.sdp;

public class Shrinker extends Items {
    private double shrinkTime;
    private double shrinkingRadius;
    public Shrinker(GeoPoint location, int itemId, boolean isTaken, double shrinkTime, double shrinkingRadius) {
        super(location, itemId, isTaken);
        this.shrinkTime = shrinkTime;
        this.shrinkingRadius = shrinkingRadius;
    }
}
