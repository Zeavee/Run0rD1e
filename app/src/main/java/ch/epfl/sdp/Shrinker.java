package ch.epfl.sdp;

public class Shrinker extends Item {
    private double shrinkTime;
    private double shrinkingRadius;
    public Shrinker(GeoPoint location, boolean isTaken, double shrinkTime, double shrinkingRadius) {
        super(location, "Shrinker", isTaken, "Shrinks your area of effect for a small time");
        this.shrinkTime = shrinkTime;
        this.shrinkingRadius = shrinkingRadius;
    }

    public double getShrinkingRadius() {return this.shrinkingRadius;}

    public double getShrinkTime() {return this.shrinkTime;}
}
