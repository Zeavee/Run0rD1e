package ch.epfl.sdp.entities.player;

import android.graphics.Color;

import ch.epfl.sdp.entities.AoeRadiusEntity;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.map.location.GeoPoint;
import ch.epfl.sdp.items.inventory.Inventory;
import ch.epfl.sdp.map.display.MapApi;
import ch.epfl.sdp.ui.map.MapsActivity;

/**
 * The player of the game
 */
public class Player extends AoeRadiusEntity {
    public final Status status = new Status();
    public final Score score = new Score();
    private final Inventory inventory = new Inventory();
    public final Wallet wallet = new Wallet();

    private String username;
    private String email;

    /**
     * A constructor for the player
     *
     * @param username the username of the player
     * @param email    the email of the player
     */
    public Player(String username, String email) {
        this(0, 0, 10, username, email, false);
    }

    /**
     * Creates a new Player
     *
     * @param longitude the longitude of the player
     * @param latitude  the latitude of the player
     * @param aoeRadius the radius of the area of effect around the player
     * @param username  the username of the player
     * @param email     the email of the player
     */
    public Player(double longitude, double latitude, double aoeRadius, String username, String email) {
        this(longitude, latitude, aoeRadius, username, email, false);
    }

    /**
     * A constructor for the player
     *
     * @param longitude the longitude of the player
     * @param latitude  the latitude of the player
     * @param aoeRadius the radius of the area of effect around the player
     * @param username  the username of the player
     * @param email     the email of the player
     * @param isPhantom the phantom mode of the player
     */
    public Player(double longitude, double latitude, double aoeRadius, String username, String email, boolean isPhantom) {
        super(new GeoPoint(longitude, latitude));
        this.setUsername(username);
        this.setEmail(email);
        this.status.setHealthPoints(Status.MAX_HEALTH, this);
        this.status.setShielded(false);
        this.status.isPhantom = isPhantom;
        this.setAoeRadius(aoeRadius);
        this.score.setGeneralScore(0, this);
        this.score.setCurrentGameScore(0, this);
        this.score.setDistanceTraveled(this);
        this.score.setDistanceTraveledAtLastCheck(0, this);
        this.wallet.money = 0;
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

    private void gotoGameOver() {
        if (Game.getInstance().getRenderer() instanceof MapsActivity)  // we don't call gameOver on mock renderers (especially since this functionality is already tested)
            ((MapsActivity) (Game.getInstance().getRenderer())).endGame();
    }

    /**
     * This method returns the inventory of the player
     *
     * @return the inventory of the player
     */
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void displayOn(MapApi mapApi) {
        if (this.equals(PlayerManager.getInstance().getCurrentUser())) {
            if (status.isPhantom) {
                mapApi.displayMarkerCircle(this, Color.WHITE, username, (int) getAoeRadius());
            } else {
                mapApi.displayMarkerCircle(this, Color.BLUE, username, (int) getAoeRadius());
            }
        } else {
            if (status.isPhantom) {
                mapApi.removeMarkers(this);
            } else {
                mapApi.displayMarkerCircle(this, Color.CYAN, username, (int) getAoeRadius());
            }
        }
    }

    /**
     * Returns a boolean to indicate that the player is alive.
     *
     * @return A boolean to indicate that the player is alive.
     */
    public boolean isAlive() {
        return status.healthPoints > 0;
    }

    public static class Status {
        /**
         * The maximum health a player can have
         */
        public final static double MAX_HEALTH = 100;
        private double healthPoints;
        private boolean isShielded;
        private boolean isShrinked;
        private boolean isPhantom;

        public Status() {
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
         * This method sets the health points of the player
         * If the amount is bigger than the maximum, we set the health points to the maximum possible
         * If the amount is smaller than 0, the player is dead and we show the game over screen
         * Only the server can set health points, so only him can have the changes sent into the remote database
         *
         * @param amount the amount of health we want to set
         * @param player the player associated to the status
         */
        public void setHealthPoints(double amount, Player player) {
            if (amount > MAX_HEALTH) {
                healthPoints = MAX_HEALTH;
            } else if (amount > 0) {
                healthPoints = amount;
            } else {
                healthPoints = 0;
            }

            if (PlayerManager.getInstance().getCurrentUser() != null && PlayerManager.getInstance().getCurrentUser().email.equals(player.getEmail()) && healthPoints == 0) {
                player.gotoGameOver();
            }

            if (PlayerManager.getInstance().isServer()) {
                PlayerManager.getInstance().addPlayersWaitingStatusUpdate(player);
            }
        }

        /**
         * This method tells if the player is shielded
         *
         * @return a boolean that tells if the player is shielded
         */
        public boolean isShielded() {
            return isShielded;
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
         * This method tells if the player is shrank
         *
         * @return a boolean that tells if the player is shrank
         */
        public boolean isShrinked() {
            return isShrinked;
        }

        /**
         * This method sets the boolean that tells if the player is shrank
         *
         * @param shrinked the boolean we want to use as the new value to know if the player is shrank
         * @param player the player associated to the status
         */
        public void setShrinked(boolean shrinked, Player player) {
            isShrinked = shrinked;

            if (PlayerManager.getInstance().isServer()) {
                PlayerManager.getInstance().addPlayersWaitingStatusUpdate(player);
            }
        }

        /**
         * Return the boolean that indicates if the player is in phantom mode.
         *
         * @return The boolean that indicates if the player is in phantom mode.
         */
        public boolean isPhantom() {
            return isPhantom;
        }

        /**
         * Set the boolean that indicates if the player is in phantom mode.
         *
         * @param phantom A boolean that indicates if the player is in phantom mode.
         * @param player the player associated to this status
         */
        public void setPhantom(boolean phantom, Player player) {
            isPhantom = phantom;

            // only the server need to upload the healthPoint for all the players
            if (PlayerManager.getInstance().isServer()) {
                PlayerManager.getInstance().addPlayersWaitingStatusUpdate(player);
            }
        }
    }

    public static class Score {
        private int generalScore;
        private int currentGameScore;
        private double distanceTraveled;
        private double distanceTraveledAtLastCheck;

        public Score() {
        }

        /**
         * This method sets the general score of the player
         *
         * @param generalScore the general score we want to set
         * @param player
         */
        public void setGeneralScore(int generalScore, Player player) {
            this.generalScore = generalScore;
        }

        /**
         * This method gets the general score of the player
         *
         * @return the general score of the player
         * @param player
         */
        public int getGeneralScore(Player player) {
            return generalScore;
        }

        /**
         * This method sets the score of the current game the player is in
         *
         * @param currentGameScore the score we want to set
         * @param player
         */
        public void setCurrentGameScore(int currentGameScore, Player player) {
            this.currentGameScore = currentGameScore;
        }

        /**
         * This method gets the score of the current game the player is in
         *
         * @return the score we want to set
         * @param player
         */
        public int getCurrentGameScore(Player player) {
            return currentGameScore;
        }

        /**
         * This method sets the distance the player traveled
         *
         * @param player
         */
        private void setDistanceTraveled(Player player) {
            distanceTraveled = 0;
        }

        /**
         * This method gets the total distance the player traveled
         *
         * @return the total distance the player traveled
         * @param player
         */
        public double getDistanceTraveled(Player player) {
            return distanceTraveled;
        }

        /**
         * This method sets the distance the player traveled since the last time we checked
         *
         * @param distanceTraveledAtLastCheck the distance we want to sets
         * @param player
         */
        private void setDistanceTraveledAtLastCheck(double distanceTraveledAtLastCheck, Player player) {
            this.distanceTraveledAtLastCheck = distanceTraveledAtLastCheck;
        }

        /**
         * This method gets the distance the player traveled since the last time we checked
         *
         * @return the distance we want to sets
         * @param player
         */
        public double getDistanceTraveledAtLastCheck(Player player) {
            return distanceTraveledAtLastCheck;
        }

        /**
         * This method updates the total distance the player traveled
         *
         * @param traveledAmount the distance we want to add
         * @param player
         */
        public void updateDistanceTraveled(double traveledAmount, Player player) {
            distanceTraveled = distanceTraveled + traveledAmount;
        }

        /**
         * This methods update the local score of the Player,
         * this is called each 10 seconds, so if the Player is alive (healthPoint is above 0), he gets 10 points
         * and if he traveled enough distance (10 meters) he gets 10 bonus points
         * @param player
         */
        public void updateLocalScore(Player player) {
            if (player.status.healthPoints > 0) {
                int bonusPoints = 10;
                if (getDistanceTraveled(player) > getDistanceTraveledAtLastCheck(player) + 10) {
                    bonusPoints += 10;
                }
                setDistanceTraveledAtLastCheck(getDistanceTraveled(player), player);
                setCurrentGameScore(getCurrentGameScore(player) + bonusPoints, player);
            }
        }
    }

    public static class Wallet {
        private int money;

        public Wallet() {
        }

        /**
         * A method to get the player's money
         *
         * @return an int which is equal to the player's money
         * @param player
         */
        public int getMoney(Player player) {
            return money;
        }

        /**
         * A method to remove money from the player
         *
         * @param amount the amount of money we want to take from the player
         * @param player
         * @return a boolean that tells if the transaction finished correctly
         */
        public boolean removeMoney(int amount, Player player) {
            if (money < amount || amount < 0) {
                return false;
            }
            money = money - amount;
            return true;
        }

        /**
         * A method to add money to the player
         *
         * @param amount the amount of money we want to give to the player
         * @param player
         */
        public void addMoney(int amount, Player player) {
            if (amount < 0) {
                return;
            }
            money = money + amount;
        }
    }
}