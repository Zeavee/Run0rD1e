package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;

public class Shield extends TimedItem  {

    public Shield(int shieldTime) {
        super(String.format("Shield %d", shieldTime), String.format("Protects you from taking damage from the enemy for %d seconds", shieldTime), shieldTime);
    }

    @Override
    public void useOn(Player player) {
        super.useOn(player);
        player.setShielded(true);
    }

    @Override
    public void stopUsingOn(Player player){
        player.setShielded(false);
    }

    public EntityType getEntityType() {
        return EntityType.SHIELD;
    }
}