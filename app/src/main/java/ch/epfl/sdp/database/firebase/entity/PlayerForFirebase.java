package ch.epfl.sdp.database.firebase.entity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.ItemBox;

public class PlayerForFirebase {
    private String username;
    private String email;
    private GeoPoint location;
    private double aoeRadius;
    private double healthPoints;
    @ServerTimestamp
    private Timestamp timestamp;

    //Each custom class must have a public constructor that takes no arguments.
    public PlayerForFirebase() {
    }

    public PlayerForFirebase(String username, String email, GeoPoint location, double aoeRadius) {
        this.username = username;
        this.email = email;
        this.location = location;
        this.aoeRadius = aoeRadius;
        this.healthPoints = 100;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public double getAoeRadius() {
        return aoeRadius;
    }

    public void setAoeRadius(double aoeRadius) {
        this.aoeRadius = aoeRadius;
    }

    public double getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(double healthPoints) {
        this.healthPoints = healthPoints;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
