package ch.epfl.sdp.database.firebase.entity;

import ch.epfl.sdp.geometry.GeoPoint;

/**
 * The in-game enemy entity to be stored in the cloud firebase
 */
public class EnemyForFirebase {
    private GeoPoint location;

    public EnemyForFirebase() {
    }

    public EnemyForFirebase(GeoPoint location) {
        this.location = location;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}
