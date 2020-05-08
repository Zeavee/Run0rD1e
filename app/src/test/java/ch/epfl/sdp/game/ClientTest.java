package ch.epfl.sdp.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.database.firebase.api.ClientMockDatabaseAPI;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.ItemBoxManager;
import ch.epfl.sdp.map.MockMap;
import static org.junit.Assert.*;

public class ClientTest {
    @Before
    public void setup() {
        Game.getInstance().clearGame();
        Game.getInstance().setMapApi(new MockMap());

        PlayerManager playerManager = PlayerManager.getInstance();
        playerManager.clear();
        playerManager.setCurrentUser(new Player("test", "test@gmail.com"));


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

        PlayerManager.getInstance().clear();

        EnemyManager.getInstance().removeAll();

        ItemBoxManager.getInstance().clearItemBoxes();
        ItemBoxManager.getInstance().clearWaitingItemBoxes();
    }

    @Test
    public void testClient() throws InterruptedException {
        Client client = new Client(new ClientMockDatabaseAPI());

        PlayerManager.getInstance().getCurrentUser().getInventory().addItem("Healthpack 10", 2);
        PlayerManager.getInstance().getCurrentUser().getInventory().useItem("Healthpack 10");

        Thread.sleep(3000);

        assertEquals(20.0, PlayerManager.getInstance().getCurrentUser().getHealthPoints(), 0.01);
    }
}
