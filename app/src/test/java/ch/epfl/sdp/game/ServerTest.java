package ch.epfl.sdp.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.database.firebase.api.ServerMockDatabaseAPI;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.ItemBoxManager;
import ch.epfl.sdp.map.MockMap;
import static org.junit.Assert.*;

public class ServerTest {
    @Before
    public void setup() {
        Game.getInstance().clearGame();
        Game.getInstance().setMapApi(new MockMap());

        PlayerManager playerManager = PlayerManager.getInstance();
        playerManager.removeAll();
        playerManager.setCurrentUser(new Player("server", "server@gmail.com"));
        playerManager.setIsServer(true);


        EnemyManager enemyManager = EnemyManager.getInstance();
        enemyManager.removeAll();

        ItemBoxManager itemBoxManager = ItemBoxManager.getInstance();
        itemBoxManager.clearItemBoxes();
        itemBoxManager.clearWaitingItemBoxes();
    }

    @After
    public void destroy() {
        Game.getInstance().clearGame();
        Game.getInstance().destroyGame();

        PlayerManager.getInstance().removeAll();

        EnemyManager.getInstance().removeAll();

        ItemBoxManager.getInstance().clearItemBoxes();
        ItemBoxManager.getInstance().clearWaitingItemBoxes();
    }

    @Test
    public void testServer() throws InterruptedException {
        Server server = new Server(new ServerMockDatabaseAPI());

        Thread.sleep(3000);

        assertEquals(2, PlayerManager.getInstance().getPlayers().size() );
    }
}
