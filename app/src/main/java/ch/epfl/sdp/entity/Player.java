package ch.epfl.sdp.entity;

import android.graphics.Color;

import ch.epfl.sdp.geometry.CartesianPoint;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.Positionable;
import ch.epfl.sdp.item.Inventory;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapApi;

public class Player extends AoeRadiusMovingEntity implements Positionable, Displayable {
    private String username;
    private String email;
    private final static double MAX_HEALTH = 100;
    private double healthPoints;
    private CartesianPoint position;
    private boolean alive;
    private boolean isShielded;
    private Inventory inventory;
    private boolean isActive;
    private int money;
    public int generalScore;
    public int currentGameScore;
    public double distanceTraveled;
    public double timeTraveled;
    public double distanceTraveledAtLastCheck;
    public double speed;

    public Player(String username, String email) {
        this(0, 0, 10, username, email);
    }

    //Constructor for the class
    public Player(double longitude, double latitude, double aoeRadius, String username, String email) {
        super();
        this.setLocation(new GeoPoint(longitude, latitude));
        this.setUsername(username);
        this.setEmail(email);
        this.setHealthPoints(100);
        this.setAlive(true);
        this.setShielded(false);
        this.setPosition(new CartesianPoint((float) longitude, (float) latitude));
        this.setAoeRadius(aoeRadius);
        this.setInventory(new Inventory());
        this.setActive(true);
        this.generalScore = 0;
        this.currentGameScore = 0;
        this.distanceTraveled = 0;
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

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setHealthPoints(double amount) {
        if (amount > 100) {
            healthPoints = 100;
        } else if (amount > 0) {
            healthPoints = amount;
            ;
        } else {
            healthPoints = 0;
            alive = false;
        }
        PlayerManager.getInstance().addPlayerWaitingHealth(this);
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

    public int getCurrentGameScore() {
        return currentGameScore;
    }

    public double getDistanceTraveled() {
        return this.distanceTraveled;
    }

    @Override
    public void displayOn(MapApi mapApi) {
        if (this.equals(PlayerManager.getInstance().getCurrentUser())) {
            mapApi.displayMarkerCircle(this, Color.BLUE, username, (int) getAoeRadius());
        } else {
            mapApi.displayMarkerCircle(this, Color.GREEN, "Other player", 100);
        }
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

    public void setGeneralScore(int generalScore) {
        this.generalScore = generalScore;
    }

    /**
     * A method to get the player's money
     *
     * @return an int which is equal to the player's money
     */
    public int getMoney() {
        return money;
    }

    /**
     * A method to remove money from the player
     *
     * @param amount the amount of money we want to take from the player
     * @return a boolean that tells if the transaction finished correctly
     */
    public boolean removeMoney(int amount) {
        if (money < amount || amount < 0) {
            return false;
        }
        money -= amount;
        return true;
    }

    /**
     * A method to add money to the player
     *
     * @param amount the amount of money we want to give to the player
     * @return a boolean that tells if the transaction finished correctly
     */
    public boolean addMoney(int amount) {
        if (amount < 0) {
            return false;
        }
        money += amount;
        return true;
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
