package ch.epfl.sdp.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.EntityConverter;
import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.LocalArea;
import ch.epfl.sdp.geometry.PointConverter;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.item.Coin;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.item.ItemBoxManager;
import ch.epfl.sdp.item.ItemFactory;
import ch.epfl.sdp.market.Market;

/**
 * Takes care of all actions that a server should perform (generating enemies, updating enemies etc.).
 */
public class Server implements Updatable {
    private static final String TAG = "Database";
    private int counter = 0;
    private ServerDatabaseAPI serverDatabaseAPI;
    private PlayerManager playerManager = PlayerManager.getInstance();
    private EnemyManager enemyManager = EnemyManager.getInstance();
    private ItemBoxManager itemBoxManager = ItemBoxManager.getInstance();
    private ItemFactory itemFactory;

    public Server(ServerDatabaseAPI serverDatabaseAPI) {
        this.serverDatabaseAPI = serverDatabaseAPI;
        itemFactory = new ItemFactory();
        initEnvironment();
    }

    @Override
    public void update() {
        if (counter <= 0) {
            sendEnemies();
            sendItemBoxes();
            sendPlayersHealth();
            sendPlayersItems();
            counter = 2 * GameThread.FPS + 1;
        }

        --counter;
    }

    private void initEnvironment() {
        serverDatabaseAPI.listenToNumOfPlayers(value -> {
            if (value.isSuccessful()) {
                Log.d(TAG, "initEnvironment: listenToNumberOf Players success");
                serverDatabaseAPI.fetchPlayers(value1 -> {
                    if (value1.isSuccessful()) {
                        for (PlayerForFirebase playerForFirebase : value1.getResult()) {
                            Player player = EntityConverter.playerForFirebaseToPlayer(playerForFirebase);
                            if (!playerManager.getCurrentUser().getEmail().equals(player.getEmail())) {
                                playerManager.addPlayer(player);
                            }
                            Log.d(TAG, "(Server) Getting Player: " + player);
                        }
                        initMarket();
                        initItemBoxes();
                        initEnemies();
                        initCoins();
                        serverDatabaseAPI.startGame(value2 -> {
                            if (value2.isSuccessful()) {
                                Game.getInstance().addToUpdateList(this);
                                Game.getInstance().initGame();
                                addPlayersPositionListener();
                                addUsedItemsListener();
                            } else Log.d(TAG, "initEnvironment: failed" + value2.getException().getMessage()); });
                    } else Log.d(TAG, "initEnvironment: failed" + value1.getException().getMessage()); });
            } else Log.d(TAG, "initEnvironment: failed" + value.getException().getMessage()); });
    }

    private void initMarket() {
        // Market --------------------------- LOCATION IMPORTANT FOR MARKET ACTIVITY TEST
        Market market = new Market(new GeoPoint(6.14, 46.22));
        Game.getInstance().addToDisplayList(market);
    }

    private void initEnemies() {
        // Enemy -------------------------------------------
        // TODO USE random enemy generator to generate enemy
        GeoPoint local = new GeoPoint(6.2419, 46.2201);
        GeoPoint enemyPos = new GeoPoint(6.3419, 46.2301);
        LocalArea localArea = new LocalArea(new RectangleArea(3500, 3500), PointConverter.geoPointToCartesianPoint(local));
        Enemy enemy = new Enemy(0, localArea, new UnboundedArea());
        enemy.setLocation(enemyPos);
        enemy.setAoeRadius(100);
        SinusoidalMovement movement = new SinusoidalMovement(PointConverter.geoPointToCartesianPoint(enemyPos));
        movement.setVelocity(25);
        movement.setAngleStep(0.1);
        movement.setAmplitude(10);
        enemy.setMovement(movement);
        enemyManager.updateEnemies(enemy);
        //  -------------------------------------------
    }

    private void initCoins() {
        int amount  = 10;
        ArrayList<Coin> coins = Coin.generateCoinsAroundLocation(playerManager.getCurrentUser().getLocation(), amount);
        for(Coin c: coins) {
            Game.getInstance().addToDisplayList(c);
            Game.getInstance().addToUpdateList(c);
        }
    }

    private void initItemBoxes() {
        // ItemBox -------------------------------------------
        Healthpack healthpack = new Healthpack(10);
        ItemBox itemBox = new ItemBox(new GeoPoint(6.14, 46.22));
        itemBox.putItems(healthpack, 2);
        Game.getInstance().addToDisplayList(itemBox);
        Game.getInstance().addToUpdateList(itemBox);

        Healthpack healthpack1 = new Healthpack(10);
        ItemBox itemBox1 = new ItemBox(new GeoPoint(6.1488, 46.2125));
        itemBox1.putItems(healthpack1, 1);
        Game.getInstance().addToDisplayList(itemBox1);
        Game.getInstance().addToUpdateList(itemBox1);

        ItemBoxManager.getInstance().addItemBox(itemBox); // puts in waiting list
        ItemBoxManager.getInstance().addItemBox(itemBox1);
        //  -------------------------------------------
    }

    private void addUsedItemsListener() {
        serverDatabaseAPI.addUsedItemsListener(value -> {
            if (value.isSuccessful()) {
                for (Map.Entry<String, ItemsForFirebase> entry : value.getResult().entrySet()) {
                    String email = entry.getKey();
                    ItemsForFirebase itemsForFirebase = entry.getValue();

                    for (Map.Entry<String, Integer> items : itemsForFirebase.getItemsMap().entrySet()) {
                        for (int i = 0; i < items.getValue(); ++i) {
                            itemFactory.getItem(items.getKey()).useOn(playerManager.getPlayersMap().get(email));
                            playerManager.getPlayersMap().get(email).getInventory().removeItem(items.getKey());
                        }
                    }
                }
            }
        });
    }

    private void addPlayersPositionListener() {
        serverDatabaseAPI.addPlayersPositionListener(value -> {
            if (value.isSuccessful()) {
                for (PlayerForFirebase playerForFirebase : value.getResult()) {
                    playerManager.getPlayersMap().get(playerForFirebase.getEmail()).setLocation(playerForFirebase.getLocation());
                    playerManager.getPlayersMap().get(playerForFirebase.getEmail()).setPosition(PointConverter.geoPointToCartesianPoint(playerForFirebase.getLocation()));
                    Log.d(TAG, "Get changes for " + playerForFirebase.getEmail() + "'s location: " + playerForFirebase.getLocation());
                }
            } else {
                Log.w(TAG, "addPlayersPositionListener: failed", value.getException());
            }
        });
    }

    private void sendEnemies() {
        serverDatabaseAPI.sendEnemies(EntityConverter.convertEnemyList(enemyManager.getEnemies()));
    }

    private void sendItemBoxes() {
        itemBoxManager.moveTakenItemBoxesToWaitingList();
        Map<String, ItemBox> itemBoxMap = itemBoxManager.getWaitingItemBoxes();

        if (!itemBoxMap.isEmpty()) {
            serverDatabaseAPI.sendItemBoxes(EntityConverter.convertItemBoxMap(itemBoxMap));
            itemBoxManager.clearWaitingItemBoxes();
        }
    }

    private void sendPlayersHealth() {
        List<Player> players = playerManager.getPlayersWaitingHealthPoint();
        if (!players.isEmpty()) {
            serverDatabaseAPI.sendPlayersHealth(EntityConverter.convertPlayerList(players));
            playerManager.getPlayersWaitingHealthPoint().clear();
        }
    }

    private void sendPlayersItems() {
        List<Player> players = playerManager.getPlayersWaitingItems();
        if (!players.isEmpty()) {
            Map<String, ItemsForFirebase> playersItemsMap = new HashMap<>();
            for (Player player : players) {
                playersItemsMap.put(player.getEmail(), EntityConverter.convertItems(player.getInventory().getItems()));
            }
            serverDatabaseAPI.sendPlayersItems(playersItemsMap);
            playerManager.getPlayersWaitingItems().clear();
        }
    }
}