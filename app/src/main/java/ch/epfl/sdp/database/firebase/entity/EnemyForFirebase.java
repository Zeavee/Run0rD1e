package ch.epfl.sdp.database.firebase.entity;

import ch.epfl.sdp.geometry.GeoPoint;

public class EnemyForFirebase {
    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    private GeoPoint location;

    public EnemyForFirebase(GeoPoint location) {
        this.location = location;
    }
}
