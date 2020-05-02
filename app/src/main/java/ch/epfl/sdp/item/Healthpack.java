package ch.epfl.sdp.item;

import android.util.Log;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;

/**
 * Class representing a healthpack
 * itemId: 1 (hardcoded)
 */
public class Healthpack extends Item {
    private int healthPackAmount;

    public Healthpack(int healthPackAmount) {
        super(String.format("Healthpack %d", healthPackAmount), String.format("Regenerates %d health points", healthPackAmount));
        this.healthPackAmount = healthPackAmount;
    }

    @Override
    public void useOn(Player player) {
        double increasedHP = player.getHealthPoints() + healthPackAmount;

        if (increasedHP > Player.getMaxHealth()) {
            increasedHP = Player.getMaxHealth();
        }

        player.setHealthPoints(increasedHP);
        Log.d("Database", "Using Healthpack on " + player.getEmail());
    }

    public double getHealthPackAmount() {
        return this.healthPackAmount;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.HEALTHPACK;
    }
}
