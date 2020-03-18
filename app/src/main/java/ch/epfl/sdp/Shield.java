package ch.epfl.sdp;

public class Shield extends Item {
    private double shieldTime;

    public Shield(GeoPoint location, boolean isTaken, double shieldTime) {
        super(location, "Shield", isTaken, "Protects you from taking damage from the enemy");
        this.shieldTime = shieldTime;
    }

    public double getShieldTime() {
        return shieldTime;
    }
}
