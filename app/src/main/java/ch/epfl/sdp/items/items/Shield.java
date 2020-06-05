package ch.epfl.sdp.items.items;

import android.util.Log;

import java.util.Locale;

import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.items.Item;
import ch.epfl.sdp.items.TimedItem;

/**
 * This item permits the player to be immune from enemies' attacks
 */
public class Shield extends TimedItem {
    /**
     * Creates a shield
     *
     * @param shieldTime the duration of the shield
     */
    public Shield(int shieldTime) {
        super(String.format(Locale.ENGLISH, "Shield %d", shieldTime), String.format(Locale.ENGLISH, "Protects you from taking damage from the enemy for %d seconds", shieldTime), shieldTime);
    }

    @Override
    public Item clone() {
        return new Shield(countTime);
    }

    @Override
    public void useOn(Player player) {
        super.useOn(player);
        player.status.setShielded(true);
        Log.d("Item", "Shield set True");
    }

    @Override
    public double getValue() {
        return countTime;
    }

    @Override
    public void stopUsingOn(Player player) {
        player.status.setShielded(false);
        Log.d("Item", "Shield set False");
    }

}