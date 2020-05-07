package ch.epfl.sdp.database.firebase.entity;

import ch.epfl.sdp.geometry.GeoPoint;

public class ItemBoxForFirebase {
    private String id;
    private GeoPoint location;
    private boolean taken;

    public ItemBoxForFirebase() {
    }

    public ItemBoxForFirebase(String id, GeoPoint location, boolean taken) {
        this.id = id;
        this.location = location;
        this.taken = taken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
