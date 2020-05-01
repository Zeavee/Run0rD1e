package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;

/**
 * Class representing a healthpack
 * itemId: 1 (hardcoded)
 */
public class Healthpack extends Item {
    private double healthPackAmount;

    public Healthpack(double healthPackAmount) {
        super(String.format("Healthpack (%f)", healthPackAmount), String.format("Regenerates %f health points", healthPackAmount));
        this.healthPackAmount = healthPackAmount;
    }

    @Override
    public Item clone() {
        return new Healthpack(healthPackAmount);
    }

    @Override
    public void use() {
        double increasedHP = PlayerManager.getCurrentUser().getHealthPoints() + healthPackAmount;
        if (increasedHP > Player.getMaxHealth()) {
            increasedHP = Player.getMaxHealth();
        }
        PlayerManager.getCurrentUser().setHealthPoints(increasedHP);
    }

    public double getHealthPackAmount() {
        return this.healthPackAmount;
    }

    /**
     * Method to get the type of the object we want to display
     *
     * @return an EntityType which is an enum of types
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.HEALTHPACK;
    }
}
