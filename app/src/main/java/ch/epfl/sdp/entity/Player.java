package ch.epfl.sdp.entity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;

import ch.epfl.sdp.artificial_intelligence.CartesianPoint;
import ch.epfl.sdp.artificial_intelligence.GenPoint;
import ch.epfl.sdp.artificial_intelligence.Localizable;
import ch.epfl.sdp.item.Inventory;
import ch.epfl.sdp.map.GeoPoint;

@IgnoreExtraProperties
public class Player extends MovingEntity implements Localizable {
    public String username;
    public String email;
    @Exclude
    public final static double MAX_HEALTH = 100;
    public int generalScore;
    public int currentGameScore;
    public double healthPoints;
    public double timeTraveled;
    public double distanceTraveled;
    public double speed;
    public double distanceTraveledAtLastCheck;
    @Exclude
    public CartesianPoint position;
    @Exclude
    public boolean alive;
    @ServerTimestamp
    public Timestamp timestamp;
    @Exclude
    private boolean isShielded;
    @Exclude
    private Inventory inventory;
    @Exclude
    private boolean isActive;

    public Player() {
        this("","");
    }

    public Player(String username, String email) {
        this(0, 0, 1, username, email);
    }

    //Constructor for the class
    public Player(double longitude, double latitude, double aoeRadius, String username, String email) {
        super();
        GeoPoint g = new GeoPoint(longitude, latitude);
        this.setLocation(g);
        this.username = username;
        this.email = email;
        this.generalScore = 0;
        this.healthPoints = 100;
        this.distanceTraveled = 0;
        this.timeTraveled = 0;
        this.speed = 0;
        this.alive = true;
        this.isShielded = false;
        this.position = new CartesianPoint((float) longitude, (float) latitude);
        this.setAoeRadius(aoeRadius);
        this.inventory = new Inventory();
        this.isActive = true;
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

    @Exclude
    public boolean isAlive() {
        return alive;
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

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setHealthPoints(double amount) {
        this.healthPoints = amount;
    }

    @Exclude
    public boolean isShielded() {return this.isShielded; }

    public void setShielded(boolean shielded) {
        isShielded = shielded;
    }

    @Exclude
    @Override
    public EntityType getEntityType() {
        return EntityType.USER;
    }

    @Exclude
    @Override
    public boolean once() {
        return false;
    }

    @Exclude
    @Override
    public GenPoint getPosition() {
        return position;
    }

    @Exclude
    public void setPosition(GenPoint genPoint) {
        this.position = genPoint.toCartesian();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

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
