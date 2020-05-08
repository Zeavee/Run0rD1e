package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.Player;

public class Shrinker extends TimedItem {
    private double shrinkingRadius;
    private int shrinkTime;

    public Shrinker(int shrinkTime, double shrinkingRadius) {
        super(String.format("Shrinker %d %f", shrinkTime, shrinkingRadius), String.format("Shrinks your area of effect (radius %d) for %f seconds", shrinkTime, shrinkingRadius), shrinkTime);
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
        double aoeRadius = player.getAoeRadius() - getShrinkingRadius();
        player.setAoeRadius(aoeRadius);
    }

    @Override
    public void stopUsingOn(Player player){
        double aoeRadius = player.getAoeRadius() + getShrinkingRadius();
        player.setAoeRadius(aoeRadius);
    }


    public double getShrinkingRadius() {return this.shrinkingRadius;}
}

