package ch.epfl.sdp.utils;

import ch.epfl.sdp.entities.enemy.EnemyManager;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.items.item_box.ItemBoxManager;

/**
 * This class cleans all the singleton instances
 */
public class JunkCleaner {
    /**
     * This method cleans the game, player manager, enemy manager and item box manager
     */
    public static void clearAll() {
        // Destroy first then clear, or else ConcurrentModificationException happens.
        Game.getInstance().destroyGame();
        Game.getInstance().clearGame();
        PlayerManager.getInstance().clear();
        EnemyManager.getInstance().clear();
        ItemBoxManager.getInstance().clear();
    }

    /**
     * This method cleans the same singletons as before, but it also clean the list of listeners of the database APIs
     *
     * @param appContainer the object that contains references to our dependencies
     */
    public static void clearAllAndListeners(AppContainer appContainer) {
        JunkCleaner.clearAll();
        appContainer.commonDatabaseAPI.cleanListeners();
        appContainer.serverDatabaseAPI.cleanListeners();
        appContainer.clientDatabaseAPI.cleanListeners();
    }
}
