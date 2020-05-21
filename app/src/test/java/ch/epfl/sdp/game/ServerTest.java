package ch.epfl.sdp.game;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.JunkCleaner;
import ch.epfl.sdp.database.firebase.GeoPointForFirebase;
import ch.epfl.sdp.database.firebase.api.CommonMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.ServerMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.map.MockMap;

import static org.junit.Assert.assertEquals;

public class ServerTest {
    ServerMockDatabaseAPI serverMockDatabaseAPI;
    CommonMockDatabaseAPI commonMockDatabaseAPI;

    @After
    public void destroy() {
        JunkCleaner.clearAll();
    }

    @Test
    public void testServer() throws InterruptedException {
        JunkCleaner.clearAll();
        setupEnvironment();
        Server server = new Server(serverMockDatabaseAPI, commonMockDatabaseAPI);
        server.start();

        assertEquals(2, PlayerManager.getInstance().getPlayers().size());
        assertEquals(33, PlayerManager.getInstance().getPlayersMap().get("client@gmail.com").getLocation().getLatitude(), 0.01);
        assertEquals(33, PlayerManager.getInstance().getPlayersMap().get("client@gmail.com").getLocation().getLongitude(), 0.01);
        assertEquals(0, PlayerManager.getInstance().getCurrentUser().getInventory().getItems().size());


        Player player = PlayerManager.getInstance().getPlayersMap().get("client@gmail.com");
        Healthpack healthpack = new Healthpack(10);
        player.getInventory().addItem(healthpack.getName());
        PlayerManager.getInstance().addPlayerWaitingItems(player);

        double healthPoints = 10.0;
        player.setHealthPoints(healthPoints);

        Thread.sleep(3000);
        assertEquals(1, serverMockDatabaseAPI.items.get("client@gmail.com").getItemsMap().size());
        assertEquals(10.0, serverMockDatabaseAPI.playerForFirebaseMap.get("client@gmail.com").getHealthPoints(), 0.01);

    }

    private void setupEnvironment() {
        Game.getInstance().setMapApi(new MockMap());
        Game.getInstance().setRenderer(new MockMap());

        Player server = new Player("server", "server@gmail.com");
        server.setLocation(new GeoPoint(33.001, 33));
        PlayerManager.getInstance().setCurrentUser(server);
        PlayerManager.getInstance().setIsServer(true);

        Map<String, UserForFirebase> userForFirebaseMap = new HashMap<>();
        Map<String, PlayerForFirebase> playerForFirebaseMap = new HashMap<>();
        List<EnemyForFirebase> enemyForFirebasesList = new ArrayList<>();
        List<ItemBoxForFirebase> itemBoxForFirebaseList = new ArrayList<>();
        Map<String, ItemsForFirebase> usedItems = new HashMap<>();
        Map<String, ItemsForFirebase> items = new HashMap<>();

        /**
         * populate All Users
         */
        UserForFirebase userForFirebase0 = new UserForFirebase("server@gmail.com", "server", 100);
        UserForFirebase userForFirebase1 = new UserForFirebase("client@gmail.com", "client", 0);
        UserForFirebase userForFirebase2 = new UserForFirebase("test@gmail.com", "test", 0);
        userForFirebaseMap.put(userForFirebase0.getEmail(), userForFirebase0);
        userForFirebaseMap.put(userForFirebase1.getEmail(), userForFirebase1);
        userForFirebaseMap.put(userForFirebase2.getEmail(), userForFirebase2);


        /**
         * polulate the players in lobby
         */
        PlayerForFirebase playerForFirebase0 = new PlayerForFirebase();
        playerForFirebase0.setUsername("server");
        playerForFirebase0.setEmail("server@gmail.com");
        playerForFirebase0.setGeoPointForFirebase(new GeoPointForFirebase(33.001,33));
        playerForFirebase0.setAoeRadius(22.0);
        playerForFirebase0.setHealthPoints(20.0);
        playerForFirebase0.setCurrentGameScore(0);

        PlayerForFirebase playerForFirebase1 = new PlayerForFirebase();
        playerForFirebase1.setUsername("client");
        playerForFirebase1.setEmail("client@gmail.com");
        playerForFirebase1.setGeoPointForFirebase(new GeoPointForFirebase(33,33));
        playerForFirebase1.setAoeRadius(22.0);
        playerForFirebase1.setHealthPoints(20.0);
        playerForFirebase1.setCurrentGameScore(0);

        playerForFirebaseMap.put(playerForFirebase0.getEmail(), playerForFirebase0);
        playerForFirebaseMap.put(playerForFirebase1.getEmail(), playerForFirebase1);

        /**
         *  Populate the enemy in lobby
         */
        EnemyForFirebase enemyForFirebase = new EnemyForFirebase(0, new GeoPointForFirebase(22, 22));
        enemyForFirebasesList.add(enemyForFirebase);

        /**
         *  Populate the itemBox in lobby
         */
        ItemBoxForFirebase itemBoxForFirebase0 = new ItemBoxForFirebase("itembox0", new GeoPointForFirebase(22,22), false);
        ItemBoxForFirebase itemBoxForFirebase1 = new ItemBoxForFirebase("itembox1", new GeoPointForFirebase(23,23), true);

        itemBoxForFirebaseList.add(itemBoxForFirebase0);
        itemBoxForFirebaseList.add(itemBoxForFirebase1);

        /**
         * Populate the usedItem for each player in the lobby
         */
        Map<String, Integer> itemsMap = new HashMap<>();
        itemsMap.put("Healthpack 10", 2);
        ItemsForFirebase itemsForFirebase = new ItemsForFirebase(itemsMap, new Date(System.currentTimeMillis()));
        usedItems.put("server@gmail.com", itemsForFirebase);
        PlayerManager.getInstance().getCurrentUser().getInventory().setItems(itemsMap);

        serverMockDatabaseAPI = new ServerMockDatabaseAPI();
        commonMockDatabaseAPI = new CommonMockDatabaseAPI();
        serverMockDatabaseAPI.hardCodedInit(userForFirebaseMap, playerForFirebaseMap, enemyForFirebasesList, itemBoxForFirebaseList, usedItems, items );
        commonMockDatabaseAPI.hardCodedInit(userForFirebaseMap, playerForFirebaseMap);
    }

}
