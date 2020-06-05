package ch.epfl.sdp.item;

import android.util.Log;

import java.util.Locale;

import ch.epfl.sdp.entity.Player;

/**
 * This item permits the player to have a smaller area of effect and thus the enemy will have more difficulties attacking the player
 */
public class Shrinker extends TimedItem {
    private final int shrinkingRadius;

    /**
     * Creates a shrinker
     *
     * @param shrinkTime      the duration of the shrinking
     * @param shrinkingRadius the new area of effect's radius
     */
    public Shrinker(int shrinkTime, int shrinkingRadius) {
        super(String.format(Locale.ENGLISH, "Shrinker %d %d", shrinkTime, shrinkingRadius), String.format(Locale.ENGLISH, "Shrinks your area of effect (radius %d) for %d seconds", shrinkTime, shrinkingRadius), shrinkTime);
        this.shrinkingRadius = shrinkingRadius;
    }

    @Override
    public Item clone() {
        return new Shrinker(countTime, shrinkingRadius);
    }

    @Override
    public void useOn(Player player) {
        if (!player.status.isShrinked()) {
            player.status.setShrinked(true, player);

            super.useOn(player);

            if (player.getAoeRadius() - getShrinkingRadius() > 0) {
                double aoeRadius = player.getAoeRadius() - getShrinkingRadius();
                player.setAoeRadius(aoeRadius);
                Log.d("Item", "Shrink set True");
            }
        }
    }

    @Override
    public double getValue() {
        return countTime + shrinkingRadius;
    }

    @Override
    public void stopUsingOn(Player player) {
        double aoeRadius = player.getAoeRadius() + getShrinkingRadius();
        player.setAoeRadius(aoeRadius);
        player.status.setShrinked(false, player);
        Log.d("Item", "Shrink set false");
    }

    /**
     * This method gets the new area of effect's radius
     *
     * @return the new area of effect's radius
     */
    public double getShrinkingRadius() {
        return this.shrinkingRadius;
    }
}

