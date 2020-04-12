package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.PlayerManager;

public class Shield extends TimedItem  {
    private int shieldTime;

    public Shield(int shieldTime) {
        super(String.format("Shield (%d)", shieldTime), String.format("Protects you from taking damage from the enemy for %d seconds", shieldTime), shieldTime);
        this.shieldTime = shieldTime;
    }

    @Override
    public Item createCopy() {
        return new Shield(shieldTime);
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