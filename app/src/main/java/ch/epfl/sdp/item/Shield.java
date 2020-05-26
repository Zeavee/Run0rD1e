package ch.epfl.sdp.item;

import android.util.Log;

import ch.epfl.sdp.entity.Player;

public class Shield extends TimedItem {
    private int shieldTime;

    public Shield(int shieldTime) {
        super(String.format("Shield %d", shieldTime), String.format("Protects you from taking damage from the enemy for %d seconds", shieldTime), shieldTime);
        this.shieldTime = shieldTime;
    }

    @Override
    public Item clone() {
        return new Shield(shieldTime);
    }

    @Override
    public void useOn(Player player) {
        super.useOn(player);
        player.setShielded(true);
        Log.d("Item","Shield set True");
    }

    /**
     * gets the value of the item
     */
    @Override
    public double getValue() {
        return shieldTime;
    }

    @Override
    public void stopUsingOn(Player player) {
        player.setShielded(false);
        Log.d("Item","Shield set False");
    }

}