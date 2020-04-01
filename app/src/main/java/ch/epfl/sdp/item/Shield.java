package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.map.GeoPoint;

public class Shield extends TimedItem  {
    private Player player;

    public Shield(GeoPoint location, boolean isTaken, int shieldTime, Player player) {
        super(location, "Shield", isTaken, "Protects you from taking damage from the enemy", shieldTime);
        this.player = player;
        player.setShielded(true);
    }

    @Override
    public void update() {
        super.update();

        if(super.counter == 0) {
            player.setShielded(false);
        }
    }
}
