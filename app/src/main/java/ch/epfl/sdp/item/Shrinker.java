package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.PlayerManager;

public class Shrinker extends TimedItem {
    private double shrinkingRadius;

    public Shrinker(int shrinkTime, double shrinkingRadius) {
        super(String.format("Shrinker (%d sec) (%f radius)", shrinkTime, shrinkingRadius), String.format("Shrinks your area of effect (radius %d) for %f seconds", shrinkTime, shrinkingRadius), shrinkTime);
        this.shrinkingRadius = shrinkingRadius;
    }

    @Override
    public void use() {
        super.use();
        PlayerManager.getCurrentUser().setAoeRadius(PlayerManager.getCurrentUser().getAoeRadius() - getShrinkingRadius());
    }

    @Override
    public void stopUsing(){
        PlayerManager.getCurrentUser().setAoeRadius(PlayerManager.getCurrentUser().getAoeRadius() + getShrinkingRadius());
    }

    public double getShrinkingRadius() {return this.shrinkingRadius;}
}

