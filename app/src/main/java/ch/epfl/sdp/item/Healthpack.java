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
        super("Healthpack","Regenerates health points");
        this.healthPackAmount = healthPackAmount;
    }

    @Override
    public void use() {
        double increasedHP = PlayerManager.getUser().getHealthPoints() + healthPackAmount;
        if (increasedHP > Player.MAX_HEALTH) {
            increasedHP = Player.MAX_HEALTH;
        }
        PlayerManager.getUser().setHealthPoints(increasedHP);
    }

    public double getHealthPackAmount() {
        return this.healthPackAmount;
    }

    public EntityType getEntityType() {
        return EntityType.HEALTHPACK;
    }
}
