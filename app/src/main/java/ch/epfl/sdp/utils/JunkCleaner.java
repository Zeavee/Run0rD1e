package ch.epfl.sdp.utils;

import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
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
        EnemyManager.getInstance().clear();
        ItemBoxManager.getInstance().clear();
    }
}
