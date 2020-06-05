package ch.epfl.sdp.item;

import android.util.Log;

import java.util.Locale;

import ch.epfl.sdp.entity.Player;

public class Shield extends TimedItem {
    private final int shieldTime;

    public Shield(int shieldTime) {
        super(String.format(Locale.ENGLISH, "Shield %d", shieldTime), String.format(Locale.ENGLISH, "Protects you from taking damage from the enemy for %d seconds", shieldTime), shieldTime);
        this.shieldTime = shieldTime;
    }

    @Override
    public Item clone() {
        return new Shield(shieldTime);
    }

    @Override
    public void useOn(Player player) {
        super.useOn(player);
        player.status.setShielded(true);
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
        player.status.setShielded(false);
        Log.d("Item","Shield set False");
    }

}