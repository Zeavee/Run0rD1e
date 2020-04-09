package ch.epfl.sdp.entity;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.GeoPoint;

/**
 * Class representing a Shelter Area
 * In a Shelter Area, players have the ability to rest without taking any damage from the enemies
 */
public class ShelterArea implements Displayable {
    private GeoPoint locaiton;
    private double aoeRadius;
    private List<Player> players; //all the players in the game (all players of the lobby)
    private List<Player> playersInShelterArea; //Players inside the zone

    public ShelterArea(GeoPoint location, double aoeRadius, ArrayList<Player> players) {
        this.locaiton = location;
        this.aoeRadius = aoeRadius;
        this.players = players;
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
    public Boolean isActive() {
        return true;
    }

    public double getAoeRadius() {
        return aoeRadius;
    }

    /**
     * isInRange() which tells if a player is within the Shelter Area or not
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
        for (Player p : players) {
            if(!playersInShelterArea.contains(p) && isInRange(p)) {
                p.setShielded(true);
                playersInShelterArea.add(p);
            }
            else if(playersInShelterArea.contains(p) && !isInRange(p)) {
                p.setShielded(false);
                playersInShelterArea.remove(p);
            }
        }
    }

    /**
     * isInShelterArea checks if a player is protected by the shelter point or not
     * @param p Player that we want to check
     * @return boolean variable to see if the player is protected by this shelter area
     */
    public boolean isInShelterArea(Player p) {
        return this.playersInShelterArea.contains(p);
    }

}
