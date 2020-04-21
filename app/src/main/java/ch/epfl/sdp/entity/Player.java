package ch.epfl.sdp.entity;

import java.util.ArrayList;

import ch.epfl.sdp.geometry.CartesianPoint;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.Positionable;
import ch.epfl.sdp.item.Inventory;

public class Player extends MovingEntity implements Positionable {
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
    }

    public static double getMaxHealth() {
        return MAX_HEALTH;
    }

    public void updateHealth(ArrayList<EnemyOutDated> enemies) {
        for (EnemyOutDated e : enemies) {
            double distance = this.getLocation().distanceTo(e.getLocation()) - this.getAoeRadius() - e.getAoeRadius();
            if (distance < 0 && !isShielded()) {
                this.setHealthPoints(this.getHealthPoints() + 1 / distance * 10); //distance is negative
            }
        }
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

    @Override
    public EntityType getEntityType() {
        return EntityType.USER;
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
}
