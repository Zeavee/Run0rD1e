package ch.epfl.sdp.entity;

import android.graphics.Color;

import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Inventory;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.map.MapsActivity;

/**
 * The player of the game
 */
public class Player extends AoeRadiusEntity {
    /**
     * The maximum health a player can have
     */
    public final static double MAX_HEALTH = 100;

    private String username;
    private String email;
    private double healthPoints;
    private boolean isShielded;
    private Inventory inventory;
    private int generalScore;
    private int currentGameScore;
    private double distanceTraveled;
    private double distanceTraveledAtLastCheck;
    private int money;

    /**
     * A constructor for the player
     *
     * @param username the username of the player
     * @param email    the email of the player
     */
    public Player(String username, String email) {
        this(0, 0, 10, username, email);
    }

    /**
     * A constructor for the player
     *
     * @param longitude the longitude of the player
     * @param latitude  the latitude of the player
     * @param aoeRadius the radius of the area of effect around the player
     * @param username  the username of the player
     * @param email     the email of the player
     */
    public Player(double longitude, double latitude, double aoeRadius, String username, String email) {
        super(new GeoPoint(longitude, latitude));
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

    /**
     * This method sets the username of the player
     *
     * @param username the username we want to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method returns the username of the player
     *
     * @return the username of the player
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * This method sets the email of the player
     *
     * @param email the email we want to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method returns the email of the player
     *
     * @return the email of the player
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * This method sets the health points of the player
     * If the amount is bigger than the maximum, we set the health points to the maximum possible
     * If the amount is smaller than 0, the player is dead and we show the game over screen
     * Only the server can set health points, so only him can have the changes sent into the remote database
     *
     * @param amount the amount of health we want to set
     */
    public void setHealthPoints(double amount) {
        if (amount > MAX_HEALTH) {
            healthPoints = MAX_HEALTH;
        } else if (amount > 0) {
            healthPoints = amount;
        } else {
            healthPoints = 0;
            gotoGameOver();
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

    /**
     * This method returns the health points of the player
     *
     * @return the health points of the player
     */
    public double getHealthPoints() {
        return healthPoints;
    }

    /**
     * This method sets the boolean that tells if the player is shielded
     *
     * @param shielded the boolean we want to use as the new value to know if the player is shielded
     */
    public void setShielded(boolean shielded) {
        isShielded = shielded;
    }

    /**
     * This method tells if the player is shielded
     *
     * @return a boolean that tells if the player is shielded
     */
    public boolean isShielded() {
        return this.isShielded;
    }

    /**
     * This method sets the inventory of the player
     *
     * @param inventory the inventory we want to set
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * This method returns the inventory of the player
     *
     * @return the inventory of the player
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * This method sets the general score of the player
     *
     * @param generalScore the general score we want to set
     */
    public void setGeneralScore(int generalScore) {
        this.generalScore = generalScore;
    }

    /**
     * This method gets the general score of the player
     *
     * @return the general score of the player
     */
    public int getGeneralScore() {
        return generalScore;
    }

    /**
     * This method sets the score of the current game the player is in
     *
     * @param currentGameScore the score we want to set
     */
    public void setCurrentGameScore(int currentGameScore) {
        this.currentGameScore = currentGameScore;
    }

    /**
     * This method gets the score of the current game the player is in
     *
     * @return the score we want to set
     */
    public int getCurrentGameScore() {
        return currentGameScore;
    }

    /**
     * This method sets the distance the player traveled
     *
     * @param distanceTraveled the distance we want to set
     */
    public void setDistanceTraveled(double distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    /**
     * This method gets the total distance the player traveled
     *
     * @return the total distance the player traveled
     */
    public double getDistanceTraveled() {
        return this.distanceTraveled;
    }

    /**
     * This method sets the distance the player traveled since the last time we checked
     *
     * @param distanceTraveledAtLastCheck the distance we want to sets
     */
    public void setDistanceTraveledAtLastCheck(double distanceTraveledAtLastCheck) {
        this.distanceTraveledAtLastCheck = distanceTraveledAtLastCheck;
    }

    /**
     * This method gets the distance the player traveled since the last time we checked
     *
     * @return the distance we want to sets
     */
    public double getDistanceTraveledAtLastCheck() {
        return distanceTraveledAtLastCheck;
    }

    /**
     * This method updates the total distance the player traveled
     *
     * @param traveledAmount the distance we want to add
     */
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
}