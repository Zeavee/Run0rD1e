package ch.epfl.sdp.entity;

import android.graphics.Color;

import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Inventory;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.map.MapsActivity;

public class Player extends AoeRadiusEntity {
    public final static double MAX_HEALTH = 100;
    private String username;
    private String email;
    private double healthPoints;
    private boolean isShielded;
    private boolean isShrinked;
    private Inventory inventory;
    private int generalScore;
    private int currentGameScore;
    private double distanceTraveled;
    private double distanceTraveledAtLastCheck;
    private int money;

    public Player(String username, String email) {
        this(0, 0, 10, username, email);
    }

    //Constructor for the class
    public Player(double longitude, double latitude, double aoeRadius, String username, String email) {
        super(new GeoPoint(longitude,latitude));
        this.setUsername(username);
        this.setEmail(email);
        this.setHealthPoints(MAX_HEALTH);
        this.setShielded(false);
        this.setAoeRadius(aoeRadius);
        this.setInventory(new Inventory());
        this.setGeneralScore(0);
        this.setCurrentGameScore(0);
        this.setDistanceTraveled(0);
        this.setDistanceTraveledAtLastCheck(0);
        this.money = 0;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setHealthPoints(double amount) {
        if (amount > MAX_HEALTH) {
            healthPoints = MAX_HEALTH;
        } else if (amount > 0) {
            healthPoints = amount;
        } else {
            healthPoints = 0;
            if(PlayerManager.getInstance().getCurrentUser().email.equals(getEmail())) {
                gotoGameOver();
            }
        }

        // only the server need to upload the healthPoint for all the players
        if (PlayerManager.getInstance().isServer()) {
            PlayerManager.getInstance().addPlayerWaitingHealth(this);
        }
    }

    private void gotoGameOver() {
        if (Game.getInstance().getRenderer() instanceof MapsActivity)  // we don't call gameOver on mock renderers (especially since this functionality is already tested)
            ((MapsActivity) (Game.getInstance().getRenderer())).endGame();
    }

    public double getHealthPoints() {
        return healthPoints;
    }

    public void setShielded(boolean shielded) {
        isShielded = shielded;
    }

    public boolean isShielded() {
        return this.isShielded;
    }

    public void setShrinked(boolean shrinked){
        isShrinked = shrinked;
    }

    public boolean isShrinked() {
        return isShrinked;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setGeneralScore(int generalScore) {
        this.generalScore = generalScore;
    }

    public int getGeneralScore() {
        return generalScore;
    }

    public void setCurrentGameScore(int currentGameScore) {
        this.currentGameScore = currentGameScore;
    }

    public int getCurrentGameScore() {
        return currentGameScore;
    }

    public void setDistanceTraveled(double distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public double getDistanceTraveled() {
        return this.distanceTraveled;
    }

    public void setDistanceTraveledAtLastCheck(double distanceTraveledAtLastCheck) {
        this.distanceTraveledAtLastCheck = distanceTraveledAtLastCheck;
    }

    public double getDistanceTraveledAtLastCheck() {
        return distanceTraveledAtLastCheck;
    }

    public void updateDistanceTraveled(double traveledAmount) {
        this.distanceTraveled = this.distanceTraveled + traveledAmount;
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
     * this is called each 10 seconds, so if the Player is alive (healthPoint is above 0), he gets 10 points
     * and if he traveled enough distance (10 meters) he gets 10 bonus points
     */
    public void updateLocalScore() {
        if (this.healthPoints > 0) {
            int bonusPoints = 10;
            if (getDistanceTraveled() > getDistanceTraveledAtLastCheck() + 10) {
                bonusPoints += 10;
            }
            setDistanceTraveledAtLastCheck(getDistanceTraveled());
            setCurrentGameScore(getCurrentGameScore() + bonusPoints);
        }
    }

    @Override
    public void displayOn(MapApi mapApi) {
        if (this.equals(PlayerManager.getInstance().getCurrentUser())) {
            mapApi.displayMarkerCircle(this, Color.BLUE, username, (int) getAoeRadius());
        } else {
            mapApi.displayMarkerCircle(this, Color.GREEN, "Other player", 100);
        }
    }

    public boolean isAlive(){
        return healthPoints > 0;
    }
}