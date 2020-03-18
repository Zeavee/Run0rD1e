package ch.epfl.sdp;


/**
 * Class representing a healthpack
 * itemId: 1 (hardcoded)
 */
public class Healthpack extends Item {
    private double healthPackAmount;

    public Healthpack(GeoPoint location, boolean isTaken, double healthPackAmount) {
        super(location,"Healthpack" , isTaken, "Regenerates health points");
        this.healthPackAmount = 25;
    }

    public double getHealthPackAmount() {
        return this.healthPackAmount;
    }

}
