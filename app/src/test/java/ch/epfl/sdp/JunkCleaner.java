package ch.epfl.sdp;

import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.ItemBoxManager;

public class JunkCleaner {
    public static void clearAll(){
        // Destroy first then clear, or else ConcurrentModificationException happens.
        Game.getInstance().destroyGame();
        Game.getInstance().clearGame();
        PlayerManager.getInstance().clear();
        EnemyManager.getInstance().removeAll();
        ItemBoxManager.getInstance().clearItemBoxes();
        ItemBoxManager.getInstance().clearWaitingItemBoxes();
    }
}
