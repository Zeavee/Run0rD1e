package ch.epfl.sdp.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.JunkCleaner;
import ch.epfl.sdp.database.firebase.api.ClientMockDatabaseAPI;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.ItemBoxManager;
import ch.epfl.sdp.map.MockMap;
import static org.junit.Assert.*;

public class ClientTest {
    ClientMockDatabaseAPI clientMockDatabaseAPI;

    @Before
    public void setup() {
        JunkCleaner.clearAll();
        Game.getInstance().setMapApi(new MockMap());
        Game.getInstance().setRenderer(new MockMap());

        PlayerManager.getInstance().setCurrentUser(new Player("test", "test@gmail.com"));
    }

    @After
    public void destroy() {
        JunkCleaner.clearAll();
    }

    @Test
    public void testClient() throws InterruptedException {
        clientMockDatabaseAPI = new ClientMockDatabaseAPI();
        Client client = new Client(clientMockDatabaseAPI);

        Healthpack healthpack = new Healthpack(10);
        PlayerManager.getInstance().getCurrentUser().getInventory().addItem(healthpack.getName(), 2);
        PlayerManager.getInstance().getCurrentUser().getInventory().useItem(healthpack.getName());

        assertEquals(20.0, PlayerManager.getInstance().getCurrentUser().getHealthPoints(), 0.01);
    }

    @Test
    public void usedItemsTest(){
        clientMockDatabaseAPI = new ClientMockDatabaseAPI();
        Client client = new Client(clientMockDatabaseAPI);

        assertTrue(clientMockDatabaseAPI.usedItems.size() == 0);

        Healthpack healthpack = new Healthpack(10);
        PlayerManager.getInstance().getCurrentUser().getInventory().addItem(healthpack.getName(), 1);
        PlayerManager.getInstance().getCurrentUser().getInventory().useItem(healthpack.getName());

        while(clientMockDatabaseAPI.usedItems.size() == 0){
            System.out.println(clientMockDatabaseAPI.usedItems.size());
        }

        assertTrue(clientMockDatabaseAPI.usedItems.size() == 1);
    }
}
