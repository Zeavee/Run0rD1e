package ch.epfl.sdp.item;

import android.util.Log;

import java.util.Locale;

import ch.epfl.sdp.entity.Player;

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
        double increasedHP = player.getHealthPoints() + healthPackAmount;
        if (increasedHP > Player.MAX_HEALTH) {
            increasedHP = Player.MAX_HEALTH;
        }

        player.setHealthPoints(increasedHP);
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
