package ch.epfl.sdp;

public class Healthpack extends Item {
    private int healthPackamount;

    public Healthpack(GeoPoint location, int itemId, boolean isTaken, int healthPackamount){
        super(location,itemId,isTaken);
        this.healthPackamount= healthPackamount;
    }
}
