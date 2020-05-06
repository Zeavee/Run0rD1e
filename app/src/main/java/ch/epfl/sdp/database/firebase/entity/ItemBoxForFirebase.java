package ch.epfl.sdp.database.firebase.entity;

import ch.epfl.sdp.geometry.GeoPoint;

public class ItemBoxForFirebase {
    private GeoPoint location;
    private boolean taken;

    public ItemBoxForFirebase() {
    }

    public ItemBoxForFirebase(GeoPoint location, boolean taken) {
        this.location = location;
        this.taken = taken;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}
