package ch.epfl.sdp.item;

import android.util.Log;

import ch.epfl.sdp.entity.Player;

public class Shrinker extends TimedItem {
    private int shrinkingRadius;
    private int shrinkTime;

    public Shrinker(int shrinkTime, int shrinkingRadius) {
        super(String.format("Shrinker %d %d", shrinkTime, shrinkingRadius), String.format("Shrinks your area of effect (radius %d) for %d seconds", shrinkTime, shrinkingRadius), shrinkTime);
        this.shrinkingRadius = shrinkingRadius;
        this.shrinkTime = shrinkTime;
    }

    @Override
    public Item clone() {
        return new Shrinker(shrinkTime, shrinkingRadius);
    }

    @Override
    public void useOn(Player player) {
        super.useOn(player);

        if(player.getAoeRadius() - getShrinkingRadius() > 0){
            double aoeRadius = player.getAoeRadius() - getShrinkingRadius();
            player.setAoeRadius(aoeRadius);
            Log.d("Item","Shrink set True");
        }
    }

    /**
     * gets the value of the item
     */
    @Override
    public double getValue() {
        return shrinkingRadius;
    }

    @Override
    public void stopUsingOn(Player player) {
        double aoeRadius = player.getAoeRadius() + getShrinkingRadius();
        player.setAoeRadius(aoeRadius);
        Log.d("Item","Shrink set false");
    }


    public double getShrinkingRadius() {
        return this.shrinkingRadius;
    }
}

