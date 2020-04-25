package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.PlayerManager;

public class Shield extends TimedItem  {

    public Shield(int shieldTime) {
        super(String.format("Shield (%d)", shieldTime), String.format("Protects you from taking damage from the enemy for %d seconds", shieldTime), shieldTime);
    }

    @Override
    public void use() {
        super.use();
        PlayerManager.getCurrentUser().setShielded(true);
    }

    @Override
    public void stopUsing(){
        PlayerManager.getCurrentUser().setShielded(false);
    }
}