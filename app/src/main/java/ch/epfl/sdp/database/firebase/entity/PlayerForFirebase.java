package ch.epfl.sdp.database.firebase.entity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Map;

import ch.epfl.sdp.geometry.GeoPoint;

/**
 * The in-game player entity to be stored in the cloud firebase
 */
public class PlayerForFirebase {
    private String username;
    private String email;
    private double latitude;
    private double longitude;
    private double aoeRadius;
    private double healthPoints;
    private int currentGameScore;
    @ServerTimestamp
    private Timestamp timestamp;

    /**
     * For Firebase each custom class must have a public constructor that takes no arguments.
     */
    public PlayerForFirebase() {
    }

    /**
     * Get the username of the playerForFirebase
     *
     * @return A string represents the username of the playerForFirebase
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the playerForFirebase
     *
     * @param username The username of the playerForFirebase
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the email of the playerForFirebase
     *
     * @return A string represents the email of the playerForFirebase
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the playerForFirebase
     *
     * @param email The email of the playerForFirebase
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the longitude of the playerForFirebase
     *
     * @return The longitude of the playerForFirebase
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Set the longitude of the playerForFirebase
     *
     * @param longitude The longitude of the playerForFirebase
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Get the latitude of the playerForFirebase
     *
     * @return The latitude of the playerForFirebase
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Set the latitude of the playerForFirebase
     *
     * @param latitude The latitude of the playerForFirebase
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Get the aoeRadius of the playerForFirebase
     *
     * @return The aoeRadius of the playerForFirebase
     */
    public double getAoeRadius() {
        return aoeRadius;
    }

    /**
     * Set the aoeRadius of the playerForFirebase
     * @param aoeRadius The aoeRadius of the playerForFirebase
     */
    public void setAoeRadius(double aoeRadius) {
        this.aoeRadius = aoeRadius;
    }

    /**
     * Get the healthPoints of the playerForFirebase
     *
     * @return The health of the playerForFirebase
     */
    public double getHealthPoints() {
        return healthPoints;
    }

    /**
     * Set the healthPoints of the playerForFirebase
     *
     * @param healthPoints The healthPoints of the playerForFirebase
     */
    public void setHealthPoints(double healthPoints) {
        this.healthPoints = healthPoints;
    }

    /**
     * Get the currentGameScore of the playerForFirebase
     *
     * @return The currentGameScore of the playerForFirebase
     */
    public int getCurrentGameScore() {
        return currentGameScore;
    }

    /**
     * Set the currentGameScore of the playerForFirebase
     *
     * @param currentGameScore The currentGameScore of the playerForFirebase
     */
    public void setCurrentGameScore(int currentGameScore) {
        this.currentGameScore = currentGameScore;
    }

    /**
     * Get the timeStamp indicating the time playerForFirebase join the lobby in Cloud Firebase
     * @return The timeStamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Set the timeStamp indicating the time playerForFirebase join the lobby in Cloud Firebase
     * @param timestamp The timeStamp
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
