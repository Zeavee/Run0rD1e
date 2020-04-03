package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.map.GeoPoint;

public class Shield extends TimedItem  {

    public Shield(int shieldTime) {
        super("Shield", "Protects you from taking damage from the enemy", shieldTime);
    }

    @Override
    public void use() {
        super.use();
        PlayerManager.getUser().setShielded(true);
    }

    @Override
    public void stopUsing(){
        PlayerManager.getUser().setShielded(false);
    }

    public EntityType getEntityType() {
        return EntityType.SHIELD;
    }
}