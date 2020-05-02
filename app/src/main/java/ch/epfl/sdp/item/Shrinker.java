package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;

public class Shrinker extends TimedItem {
    private double shrinkingRadius;

    public Shrinker(int shrinkTime, double shrinkingRadius) {
        super(String.format("Shrinker %d %f", shrinkTime, shrinkingRadius), String.format("Shrinks your area of effect (radius %d) for %f seconds", shrinkTime, shrinkingRadius), shrinkTime);
        this.shrinkingRadius = shrinkingRadius;
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

    public EntityType getEntityType() {
        return EntityType.SHRINKER;
    }

    public double getShrinkingRadius() {return this.shrinkingRadius;}
}

