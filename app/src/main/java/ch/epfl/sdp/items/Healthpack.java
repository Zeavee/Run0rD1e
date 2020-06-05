package ch.epfl.sdp.items;

import android.util.Log;

import java.util.Locale;

import ch.epfl.sdp.entities.player.Player;


/**
 * Class representing a healthpack
 * itemId: 1 (hardcoded)
 */
public class Healthpack extends Item {
    private final int healthPackAmount;

    public Healthpack(int healthPackAmount) {
        super(String.format(Locale.ENGLISH, "Healthpack %d", healthPackAmount), String.format(Locale.ENGLISH, "Regenerates %d health points", healthPackAmount));
        this.healthPackAmount = healthPackAmount;
    }

    public Item clone() {
        return new Healthpack(healthPackAmount);
    }

    @Override
    public void useOn(Player player) {
        double increasedHP = player.status.getHealthPoints() + healthPackAmount;
        if (increasedHP > Player.Status.MAX_HEALTH) {
            increasedHP = Player.Status.MAX_HEALTH;
        }

        player.status.setHealthPoints(increasedHP, player);
        Log.d("Database", "Using Healthpack on " + player.getEmail());
    }

    /**
     * gets the value of the item
     */
    @Override
    public double getValue() {
        return healthPackAmount;
    }

}
