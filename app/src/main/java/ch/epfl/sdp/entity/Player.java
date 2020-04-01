package ch.epfl.sdp.entity;

import androidx.room.Ignore;

import java.util.ArrayList;

import ch.epfl.sdp.artificial_intelligence.CartesianPoint;
import ch.epfl.sdp.artificial_intelligence.GenPoint;
import ch.epfl.sdp.artificial_intelligence.Localizable;
import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.map.GeoPoint;

public class Player extends MovingEntity implements Localizable, Updatable {
    public String username;
    public String email;
    public CartesianPoint position;
    public int score;
    public double healthPoints;
    public double timeTraveled;
    public double distanceTraveled;
    public double speed;
    public boolean alive;

    @Ignore
    public final static double MAX_HEALTH = 100;

    private boolean isShielded;
    private Inventory inventory;

    public Player() {
        super();
    }

    public Player(String username, String email) {
        this(0, 0, 1, username, email);
    }

    //Contstructor for the class
    public Player(double longitude, double latitude, double aoeRadius, String username, String email) {
        GeoPoint g = new GeoPoint(longitude, latitude);
        this.setLocation(g);
        this.username = username;
        this.email = email;
        this.score = 0;
        this.healthPoints = 100;
        this.distanceTraveled = 0;
        this.timeTraveled = 0;
        this.speed = 0;
        this.alive = true;
        this.isShielded = false;
        this.position = new CartesianPoint((float) longitude, (float) latitude);
        this.setAoeRadius(aoeRadius);
        this.inventory = new Inventory(this);
    }

    public void updateHealth(ArrayList<EnemyOutDated> enemies) {
        for (EnemyOutDated e : enemies) {
            double distance = this.getLocation().distanceTo(e.getLocation()) - this.getAoeRadius() - e.getAoeRadius();
            if (distance < 0 && !isShielded) {
                this.healthPoints = this.healthPoints + 1/distance * 10; //distance is negative
            }
        }
    }

    public double getHealthPoints() {
        return healthPoints;
    }

    public boolean isAlive() {
        return alive;
    }

   public double getSpeed() {
        return speed;
    }

    public double getTimeTraveled() {
        return timeTraveled;
    }

    public int getScore() {
        return score;
    }

    public double getDistanceTraveled() {
        return this.distanceTraveled;
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

    public boolean isShielded() {return this.isShielded; }

    @Override
    public EntityType getEntityType() {
        return EntityType.USER;
    }

    @Override
    public GenPoint getPosition() {
        return position;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPosition(CartesianPoint position) {
        this.position = position;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTimeTraveled(double timeTraveled) {
        this.timeTraveled = timeTraveled;
    }

    public void setDistanceTraveled(double distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setShielded(boolean shielded) {
        isShielded = shielded;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setIsShielded(boolean isShielded) {this.isShielded = isShielded;}

    @Override
    public void update() {

    }


}
