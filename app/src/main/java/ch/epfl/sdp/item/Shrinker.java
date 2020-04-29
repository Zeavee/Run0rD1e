package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
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
        Player currentUser = PlayerManager.getInstance().getCurrentUser();
        double aoeRadius = currentUser.getAoeRadius() - getShrinkingRadius();
        currentUser.setAoeRadius(aoeRadius);
    }

    @Override
    public void stopUsing(){
        Player currentUser = PlayerManager.getInstance().getCurrentUser();
        double aoeRadius = currentUser.getAoeRadius() + getShrinkingRadius();
        currentUser.setAoeRadius(aoeRadius);
    }

    public EntityType getEntityType() {
        return EntityType.SHRINKER;
    }

    public double getShrinkingRadius() {return this.shrinkingRadius;}
}

