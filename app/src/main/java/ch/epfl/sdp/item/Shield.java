package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.PlayerManager;

public class Shield extends TimedItem  {
    private int shieldTime;

    public Shield(int shieldTime) {
        super(String.format("Shield (%d)", shieldTime), String.format("Protects you from taking damage from the enemy for %d seconds", shieldTime), shieldTime);
        this.shieldTime = shieldTime;
    }

    @Override
    public Item clone() {
        return new Shield(shieldTime);
    }

    @Override
    public void use() {
        super.use();
        PlayerManager.getCurrentUser().setShielded(true);
    }

    public double getValue() {
        return shieldTime;
    }

    @Override
    public void stopUsing(){
        PlayerManager.getCurrentUser().setShielded(false);
    }

}