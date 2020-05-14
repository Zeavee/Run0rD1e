package ch.epfl.sdp.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.JunkCleaner;
import ch.epfl.sdp.database.firebase.GeoPointForFirebase;
import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.ServerMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.item.ItemBoxManager;
import ch.epfl.sdp.map.MockMap;
import static org.junit.Assert.*;

public class ServerTest {
    ServerMockDatabaseAPI serverMockDatabaseAPI;

    @Before
    public void setup() {
        JunkCleaner.clearAll();

        Game.getInstance().setMapApi(new MockMap());
        Game.getInstance().setRenderer(new MockMap());

        PlayerManager.getInstance().setCurrentUser(new Player("server", "server@gmail.com"));
        PlayerManager.getInstance().setIsServer(true);

        serverMockDatabaseAPI = new ServerMockDatabaseAPI();
    }

    @After
    public void destroy() {
        JunkCleaner.clearAll();
    }

    @Test
    public void testServer() {
        serverMockDatabaseAPI.hardCodedInit();
        Server server = new Server(serverMockDatabaseAPI);
        assertEquals(2, PlayerManager.getInstance().getPlayers().size());
    }

    @Test
    public void sendPlayersHealthWork(){
        PlayerForFirebase playerForFirebase = new PlayerForFirebase();
        playerForFirebase.setEmail("test");
        playerForFirebase.setUsername("test");
        playerForFirebase.setGeoPointForFirebase(new GeoPointForFirebase(22,22));
        playerForFirebase.setAoeRadius(22.0);
        playerForFirebase.setHealthPoints(100.0);
        playerForFirebase.setCurrentGameScore(0);

        serverMockDatabaseAPI.addPlayerForFirebase(playerForFirebase);
        Server server = new Server(serverMockDatabaseAPI);

        Player player = PlayerManager.getInstance().getPlayersMap().get(playerForFirebase.getEmail());

        double healthPoints = 1;

        assertTrue(serverMockDatabaseAPI.getPlayerForFirebaseList().get(0).getHealthPoints() == Player.getMaxHealth());

        player.setHealthPoints(healthPoints);

        while (PlayerManager.getInstance().getPlayersWaitingHealthPoint().size() > 0) {
            server.update();
        }

        assertTrue( serverMockDatabaseAPI.getPlayerForFirebaseList().get(0).getHealthPoints() == healthPoints);
    }

    @Test
    public void sendPlayersItemsWork(){
        PlayerForFirebase playerForFirebase = new PlayerForFirebase();
        playerForFirebase.setEmail("test");
        playerForFirebase.setUsername("test");
        playerForFirebase.setGeoPointForFirebase(new GeoPointForFirebase(22,22));
        playerForFirebase.setAoeRadius(22.0);
        playerForFirebase.setHealthPoints(100.0);
        playerForFirebase.setCurrentGameScore(0);

        serverMockDatabaseAPI.addPlayerForFirebase(playerForFirebase);
        Server server = new Server(serverMockDatabaseAPI);

        Player player = PlayerManager.getInstance().getPlayersMap().get(playerForFirebase.getEmail());

        assertTrue(serverMockDatabaseAPI.items.get(player.getEmail()) == null);

        Healthpack healthpack = new Healthpack(10);
        player.getInventory().addItem(healthpack.getName());
        PlayerManager.getInstance().addPlayerWaitingItems(player);

        while (PlayerManager.getInstance().getPlayersWaitingItems().size() > 0) {
            server.update();
        }

        assertTrue( serverMockDatabaseAPI.items.get(player.getEmail()).getItemsMap().size() == 1);
    }
}
