package ch.epfl.sdp.database.firebase.entity;

import ch.epfl.sdp.database.firebase.GeoPointForFirebase;
import ch.epfl.sdp.geometry.GeoPoint;

public class ItemBoxForFirebase {
    private String id;
    private GeoPointForFirebase location;
    private boolean taken;

    public ItemBoxForFirebase(String id, GeoPointForFirebase location, boolean taken) {
        this.id = id;
        this.location = location;
        this.taken = taken;
    }

    public ItemBoxForFirebase() {
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public GeoPointForFirebase getLocation() {
        return location;
    }

    public void setLocation(GeoPointForFirebase location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
