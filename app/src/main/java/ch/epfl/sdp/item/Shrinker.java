package ch.epfl.sdp.item;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.map.GeoPoint;

public class Shrinker extends TimedItem {
    Player player;
    private double shrinkingRadius;

    public Shrinker(GeoPoint location, boolean isTaken, int shrinkTime, double shrinkingRadius, Player player) {
        super(location, "Shrinker", isTaken, "Shrinks your area of effect for a small time", shrinkTime);
        this.shrinkingRadius = shrinkingRadius;
        this.player = player;
    }

    public double getShrinkingRadius() {return this.shrinkingRadius;}

    @Override
    public void update() {
        super.update();
        System.out.println(String.format("Update shrinker, count %d", counter));

        if(super.counter == 0) {
            player.setAoeRadius(player.getAoeRadius() + getShrinkingRadius());
        }
    }
}