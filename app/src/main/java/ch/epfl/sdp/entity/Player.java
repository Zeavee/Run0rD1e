package ch.epfl.sdp.entity;

import android.graphics.Color;

import ch.epfl.sdp.geometry.CartesianPoint;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.Positionable;
import ch.epfl.sdp.item.Inventory;
import ch.epfl.sdp.map.MapApi;

public class Player extends AoeRadiusMovingEntity implements Positionable {
    private String username;
    private String email;
    private final static double MAX_HEALTH = 100;
    private double score;
    private double healthPoints;
    private CartesianPoint position;
    private boolean alive;
    private boolean isShielded;
    private Inventory inventory;
    private boolean isActive;
    public int generalScore;
    public int currentGameScore;
    public double distanceTraveled;
    public double timeTraveled;
    public double distanceTraveledAtLastCheck;
    public double speed;
    public int money;

    public Player(String username, String email) {
        this(0, 0, 10, username, email);
    }

    //Constructor for the class
    public Player(double longitude, double latitude, double aoeRadius, String username, String email) {
        super();
        this.setLocation(new GeoPoint(longitude, latitude));
        this.setUsername(username);
        this.setEmail(email);
        this.setScore(0);
        this.setHealthPoints(100);
        this.setAlive(true);
        this.setShielded(false);
        this.setPosition(new CartesianPoint((float) longitude, (float) latitude));
        this.setAoeRadius(aoeRadius);
        this.setInventory(new Inventory());
        this.setActive(true);
        this.distanceTraveled = 0;
        this.generalScore = 0;
        this.speed = 0;
        this.money = 0;
    }

    public static double getMaxHealth() {
        return MAX_HEALTH;
    }

    public double getHealthPoints() {
        return healthPoints;
    }

    public boolean isAlive() {
        return alive;
    }

    public double getScore() {
        return score;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setHealthPoints(double amount) {
        this.healthPoints = amount;
    }

    public boolean isShielded() {
        return this.isShielded;
    }

    public void setShielded(boolean shielded) {
        isShielded = shielded;
    }

    public double getSpeed() {
        return speed;
    }

    public double getTimeTraveled() {
        return timeTraveled;
    }

    public int getGeneralScore() {
        return generalScore;
    }

    public double getDistanceTraveled() {
        return this.distanceTraveled;
    }

    @Override
    public void displayOn(MapApi mapApi) {
        mapApi.displayMarkerCircle(this, Color.YELLOW, "Other player", 100);
    }

    @Override
    public boolean isOnce() {
        return false;
    }

    @Override
    public CartesianPoint getPosition() {
        return position;
    }

    public void setPosition(CartesianPoint position) {
        this.position = position;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setDistanceTraveled(double distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }
  
    /**
     * This methods update the local score of the Player,
     * this is called each 10 seconds, so if the Player is alive, he gets 10 points
     * and if he traveled enough distance (10 meters) he gets 10 bonus points
     */
    public void updateLocalScore() {
        if (isAlive()) {
            int bonusPoints = 10;
            if (distanceTraveled > distanceTraveledAtLastCheck + 10) {
                bonusPoints += 10;
            }
            distanceTraveledAtLastCheck = distanceTraveled;
            currentGameScore += bonusPoints;
        }
    }
}