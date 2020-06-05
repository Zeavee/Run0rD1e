package ch.epfl.sdp.game;

import org.junit.After;
import org.junit.Test;

import ch.epfl.sdp.database.firebase.api.CommonMockDatabaseAPI;
import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.game.game_architecture.Solo;
import ch.epfl.sdp.map.MockMap;
import ch.epfl.sdp.utils.JunkCleaner;

import static org.junit.Assert.assertTrue;

public class SoloTest {
    @After
    public void destroy() {
        JunkCleaner.clearAll();
    }

    @Test
    public void testSolo() throws InterruptedException {
        JunkCleaner.clearAll();
        Game.getInstance().setMapApi(new MockMap());
        Game.getInstance().setRenderer(new MockMap());

        PlayerManager.getInstance().setCurrentUser(new Player(50, 50, 10, "solo", "solo@gmail.com"));

        PlayerManager.getInstance().setSoloMode(true);
        PlayerManager.getInstance().setIsServer(false);

        Solo solo = new Solo(new CommonMockDatabaseAPI());

        solo.start();

        Thread.sleep(1000);

        PlayerManager.getInstance().getCurrentUser().status.setHealthPoints(0, PlayerManager.getInstance().getCurrentUser());

        Thread.sleep(1000);

        assertTrue(solo.isGameEnd());
    }
}
