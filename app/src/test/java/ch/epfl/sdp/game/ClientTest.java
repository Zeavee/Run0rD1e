package ch.epfl.sdp.game;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.JunkCleaner;
import ch.epfl.sdp.artificial_intelligence.Behaviour;
import ch.epfl.sdp.database.firebase.GeoPointForFirebase;
import ch.epfl.sdp.database.firebase.api.ClientMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.CommonMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.CircleArea;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.ItemBoxManager;
import ch.epfl.sdp.map.MockMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientTest {
    ClientMockDatabaseAPI clientMockDatabaseAPI;
    CommonMockDatabaseAPI commonMockDatabaseAPI;

    GeoPoint center;

    @After
    public void teardown() {
        JunkCleaner.clearAll();
    }

    @Test
    public void testClient() {
        JunkCleaner.clearAll();
        setupEnvironment();
        Client client = new Client(clientMockDatabaseAPI, commonMockDatabaseAPI);
        client.start();

        assertEquals(2, PlayerManager.getInstance().getPlayers().size());
        assertEquals(1, EnemyManager.getInstance().getEnemies().size());
        assertEquals(1, ItemBoxManager.getInstance().getItemBoxes().size());
        assertEquals(2, PlayerManager.getInstance().getCurrentUser().getInventory().size());

        Player user = PlayerManager.getInstance().getCurrentUser();
        user.setLocation(new GeoPoint(100, 100));

        Game.getInstance().getDisplayables().forEach((d) -> {
            if (d instanceof CircleArea) {
                assertTrue(center.distanceTo(d.getLocation()) < 0.01);
                assertEquals(300, ((CircleArea) d).getRadius(), 0.01);
            }
        });

        while (commonMockDatabaseAPI.playerForFirebaseMap.get(user.getEmail()).getGeoPointForFirebase().getLatitude() != 100 &&
                commonMockDatabaseAPI.playerForFirebaseMap.get(user.getEmail()).getGeoPointForFirebase().getLongitude() != 100){
        }

        assertEquals(100, commonMockDatabaseAPI.playerForFirebaseMap.get(PlayerManager.getInstance().getCurrentUser().getEmail()).getGeoPointForFirebase().getLatitude(), 0.01);
        assertEquals(100, commonMockDatabaseAPI.playerForFirebaseMap.get(PlayerManager.getInstance().getCurrentUser().getEmail()).getGeoPointForFirebase().getLongitude(), 0.01);
    }

    private void setupEnvironment() {
        Game.getInstance().setMapApi(new MockMap());
        Game.getInstance().setRenderer(new MockMap());

        PlayerManager.getInstance().setCurrentUser(new Player("client", "client@gmail.com"));
        PlayerManager.getInstance().setIsServer(false);

        Map<String, UserForFirebase> userForFirebaseMap = new HashMap<>();
        Map<String, PlayerForFirebase> playerForFirebaseMap = new HashMap<>();
        List<EnemyForFirebase> enemyForFirebasesList = new ArrayList<>();
        List<ItemBoxForFirebase> itemBoxForFirebaseList = new ArrayList<>();
        ItemsForFirebase userItems;

        /**
         * populate All Users
         */
        UserForFirebase userForFirebase0 = new UserForFirebase("server@gmail.com", "server", 0);
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
        playerForFirebase0.setGeoPointForFirebase(new GeoPointForFirebase(22,22));
        playerForFirebase0.setAoeRadius(22.0);
        playerForFirebase0.setHealthPoints(20.0);
        playerForFirebase0.setCurrentGameScore(0);

        PlayerForFirebase playerForFirebase1 = new PlayerForFirebase();
        playerForFirebase1.setUsername("client");
        playerForFirebase1.setEmail("client@gmail.com");
        playerForFirebase1.setGeoPointForFirebase(new GeoPointForFirebase(22,22));
        playerForFirebase1.setAoeRadius(22.0);
        playerForFirebase1.setHealthPoints(20.0);
        playerForFirebase1.setCurrentGameScore(0);

        playerForFirebaseMap.put(playerForFirebase0.getEmail(), playerForFirebase0);
        playerForFirebaseMap.put(playerForFirebase1.getEmail(), playerForFirebase1);

        /**
         *  Populate the enemy in lobby
         */
        EnemyForFirebase enemyForFirebase = new EnemyForFirebase(0, Behaviour.WAIT, new GeoPointForFirebase(22, 22));
        enemyForFirebasesList.add(enemyForFirebase);

        /**
         *  Populate the itemBox in lobby
         */
        ItemBoxForFirebase itemBoxForFirebase0 = new ItemBoxForFirebase("itembox0", new GeoPointForFirebase(22,22), false);
        ItemBoxForFirebase itemBoxForFirebase1 = new ItemBoxForFirebase("itembox1", new GeoPointForFirebase(23,23), true);

        itemBoxForFirebaseList.add(itemBoxForFirebase0);
        itemBoxForFirebaseList.add(itemBoxForFirebase1);

        /**
         * Populate the Items of Current User
         */
        Map<String, Integer> itemsMap = new HashMap<>();
        itemsMap.put("Healthpack 10", 2);
        userItems = new ItemsForFirebase(itemsMap, new Date(System.currentTimeMillis()));

        center = new GeoPoint(22, 22);
        CircleArea circleArea = new CircleArea(300, center);

        clientMockDatabaseAPI = new ClientMockDatabaseAPI();
        commonMockDatabaseAPI = new CommonMockDatabaseAPI();
        clientMockDatabaseAPI.hardCodedInit(userForFirebaseMap, playerForFirebaseMap, enemyForFirebasesList, itemBoxForFirebaseList, userItems, circleArea.toString());
        commonMockDatabaseAPI.hardCodedInit(userForFirebaseMap, playerForFirebaseMap);
    }
}
