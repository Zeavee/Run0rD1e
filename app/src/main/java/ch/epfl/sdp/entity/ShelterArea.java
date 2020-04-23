package ch.epfl.sdp.entity;

import java.util.ArrayList;

import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.Displayable;

/**
 * Class representing a Shelter Area
 * In a Shelter Area, players have the ability to rest without taking any damage from the enemies
 */
public class ShelterArea implements Displayable, Updatable {
    private GeoPoint locaiton;
    private double aoeRadius;
    private ArrayList<Player> playersInShelterArea; //Players inside the ShelterArea

    public ShelterArea(GeoPoint location, double aoeRadius) {
        this.locaiton = location;
        this.aoeRadius = aoeRadius;
        this.playersInShelterArea = new ArrayList<>();
    }

    @Override
    public GeoPoint getLocation() {
        return this.locaiton;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.SHELTER;
    }

    @Override
    public boolean isOnce() {
        return false;
    }

    public double getAoeRadius() {
        return aoeRadius;
    }

    /**
     * isInRange() which tells if a player is within the Shelter Area or not
     *
     * @param p Player which we may or may not be in the Shelter Area
     * @return Boolean which tells if a player is in the Shelter Area or not.
     */
    private boolean isInRange(Player p) {
        double distance = this.locaiton.distanceTo(p.getLocation()) - this.aoeRadius - p.getAoeRadius();
        return (distance < 0);
    }

    /**
     * shelter() protects players that are in the zone and unprotects players that leave the zone
     * This method must be called at every iteration of the game loop
     */
    public void shelter() {
        for (Player p : PlayerManager.getPlayers()) {
            if (!playersInShelterArea.contains(p) && isInRange(p)) {
                p.setShielded(true);
                playersInShelterArea.add(p);
            } else if (playersInShelterArea.contains(p) && !isInRange(p)) {
                p.setShielded(false);
                playersInShelterArea.remove(p);
            }
        }
    }

    /**
     * isInShelterArea checks if a player is protected by the shelter point or not
     *
     * @param p Player that we want to check
     * @return boolean variable to see if the player is protected by this shelter area
     */
    public boolean isInShelterArea(Player p) {
        return this.playersInShelterArea.contains(p);
    }

    public ArrayList<Player> getPlayersInShelterArea() {
        return this.playersInShelterArea;
    }

    @Override
    public void update() {
        shelter();
    }
}