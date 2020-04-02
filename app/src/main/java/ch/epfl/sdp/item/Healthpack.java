package ch.epfl.sdp.item;


import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.map.GeoPoint;

/**
 * Class representing a healthpack
 * itemId: 1 (hardcoded)
 */
public class Healthpack extends Item {
    private double healthPackAmount;



    public Healthpack(GeoPoint location, boolean isTaken, double healthPackAmount) {
        super(location,"Healthpack" , isTaken, "Regenerates health points");
        this.healthPackAmount = healthPackAmount;
    }

    public void increaseHealthPlayer(Player player, double maxHealth) {
        double increasedHP = player.getHealthPoints() + healthPackAmount;
        if (increasedHP > maxHealth) {
            increasedHP = maxHealth;
        }
        player.setHealthPoints(increasedHP);
    }

    public double getHealthPackAmount() {
        return this.healthPackAmount;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.HEALTHPACK;
    }
}
