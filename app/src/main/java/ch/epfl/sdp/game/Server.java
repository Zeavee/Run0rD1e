package ch.epfl.sdp.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.firebase.entityForFirebase.EntityConverter;
import ch.epfl.sdp.database.firebase.entityForFirebase.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.UserForFirebase;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.item.ItemBoxManager;
import ch.epfl.sdp.item.ItemFactory;

/**
 * Takes care of all actions that a server should perform (generating enemies, updating enemies etc.).
 */
public class Server extends StartGameController implements Updatable {
    private static final String TAG = "Database";
    private int counter;
    private boolean gameStarted;
    private final ServerDatabaseAPI serverDatabaseAPI;
    private final PlayerManager playerManager = PlayerManager.getInstance();
    private final EnemyManager enemyManager = EnemyManager.getInstance();
    private final ItemBoxManager itemBoxManager = ItemBoxManager.getInstance();
    private final ItemFactory itemFactory;
    private Area gameArea;
    private long signal;
    private Runnable endGame;

    /**
     * Constructor for the Server
     *
     * @param serverDatabaseAPI the API for accessing the remote database used by the server
     * @param commonDatabaseAPI the API for accessing the remote database used by the client and the server
     */
    public Server(ServerDatabaseAPI serverDatabaseAPI, CommonDatabaseAPI commonDatabaseAPI, Runnable endGame) {
        super(commonDatabaseAPI);
        this.serverDatabaseAPI = serverDatabaseAPI;
        this.counter = 0;
        this.gameStarted = false;
        this.itemFactory = new ItemFactory();
        this.endGame = endGame;
        this.signal = 0;
    }

    @Override
    public void start() {
        if (!gameStarted) {
            gameStarted = true;

            serverDatabaseAPI.listenToNumOfPlayers(value -> {
                if (value.isSuccessful()) {
                    Log.d(TAG, "initEnvironment: listenToNumberOf Players success");
                    fetchPlayers();
                } else Log.d(TAG, "initEnvironment: failed" + value.getException().getMessage());
            });
        }
    }

    @Override
    public void update() {
        if (counter % (2 * GameThread.FPS) == 0) {
            sendUserPosition();
            sendGameArea();
            sendEnemies();
            sendItemBoxes();
            sendPlayersStatus();
            sendPlayersItems();
            checkIfWon();
        }

        if (counter % (5 * GameThread.FPS) == 0) {
            serverDatabaseAPI.sendServerAliveSignal(signal);
            ++signal;
        }

        // Update the current game score in 10 seconds
        if (counter % (10 * GameThread.FPS) == 0) {
            Log.d(TAG, "update: update the score of player");
            updateInGameScoreOfPlayer();
        }

        if (counter % (30 * GameThread.FPS) == 0) {
            generateEnemy(EnemyManager.getInstance());

            // reset tht counter to avoid overflow
            counter = 0;
        }

        counter++;
    }

    private void checkIfWon() {
        boolean isWinner = true;
        for (Player player : playerManager.getPlayers()) {
            if (player.status.getHealthPoints() > 0 && player != playerManager.getCurrentUser()) {
                isWinner = false;
                break;
            }
        }
        if (isWinner) {
            updateGeneralScore();
            endGame.run();
        }
    }

    private void sendGameArea() {
        serverDatabaseAPI.sendGameArea(gameArea);
    }

    private void fetchPlayers() {
        commonDatabaseAPI.fetchPlayers(playerManager.getLobbyDocumentName(), value1 -> {
            if (value1.isSuccessful()) {
                addPlayersInPlayerManager(playerManager, value1.getResult());
                List<String> playersEmailList = new ArrayList<>(playerManager.getPlayersMap().keySet());
                fetchGeneralScore(playersEmailList);
            } else Log.d(TAG, "initEnvironment: failed" + value1.getException().getMessage());
        });
    }

    private void fetchGeneralScore(List<String> playersEmailList) {
        serverDatabaseAPI.fetchGeneralScoreForPlayers(playersEmailList, value -> {
            if (value.isSuccessful()) {
                for (UserForFirebase userForFirebase : value.getResult()) {
                    Objects.requireNonNull(playerManager.getPlayersMap().get(userForFirebase.getEmail())).setGeneralScore(userForFirebase.getGeneralScore());
                    Log.d(TAG, "init environment: fetch general score " + userForFirebase.getUsername() + " with score " + userForFirebase.getGeneralScore());
                }
                gameArea = initGameArea();
                createRandomEnemyGenerator(gameArea);
                generateEnemy(enemyManager);
                startGame();
                initItemBox(gameArea);
                initGameObjects(gameArea);
            } else
                Log.d(TAG, "init environment: fetch general score failed " + value.getException().getMessage());
        });
    }

    private void startGame() {
        serverDatabaseAPI.startGame(value -> {
            if (value.isSuccessful()) {
                Game.getInstance().addToUpdateList(this);
                PlayerManager.getInstance().displayPlayers();
                Game.getInstance().initGame();
                addPlayersListener();
                addUsedItemsListener();
            } else Log.d(TAG, "initEnvironment: failed" + value.getException().getMessage());
        });
    }


    private void addUsedItemsListener() {
        serverDatabaseAPI.addUsedItemsListener(value -> {
            if (value.isSuccessful()) {
                for (Map.Entry<String, ItemsForFirebase> entry : value.getResult().entrySet()) {
                    String email = entry.getKey();
                    ItemsForFirebase itemsForFirebase = entry.getValue();

                    for (Map.Entry<String, Integer> items : itemsForFirebase.getItemsMap().entrySet()) {
                        int usedCount = items.getValue();
                        for (int i = 0; i < usedCount; i++) {
                            itemFactory.getItem(items.getKey()).useOn(playerManager.getPlayersMap().get(email));
                            Objects.requireNonNull(playerManager.getPlayersMap().get(email)).getInventory().removeItem(items.getKey());
                        }
                    }
                }
            }
        });
    }

    private void addPlayersListener() {
        serverDatabaseAPI.addCollectionListener(PlayerForFirebase.class, PlayerManager.PLAYER_COLLECTION_NAME, value -> {
            if (value.isSuccessful()) {
                for (PlayerForFirebase playerForFirebase : value.getResult()) {
                    Player player = playerManager.getPlayersMap().get(playerForFirebase.getEmail());
                    GeoPoint location = EntityConverter.geoPointForFirebaseToGeoPoint(playerForFirebase.getGeoPointForFirebase());

                    if (Objects.requireNonNull(player).getLocation() != null) {
                        Log.d(TAG, "addPlayersPositionListener: before " + playerForFirebase.getUsername() + " " + player.getLocation().getLatitude() + " " + player.getLocation().getLongitude());
                        Log.d(TAG, "addPlayersPositionListener: after " + playerForFirebase.getUsername() + " " + location.getLatitude() + " " + location.getLongitude());
                        double traveledDistance = player.getLocation().distanceTo(location);
                        player.updateDistanceTraveled(traveledDistance);

                        // update the location of the player
                        player.setLocation(location);
                        player.setAoeRadius(playerForFirebase.getAoeRadius());
                        Log.d(TAG, "addPlayersPositionListener: traveledDistance" + traveledDistance);
                    }
                }
            } else Log.w(TAG, "addPlayersPositionListener: failed", value.getException());
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

    private void sendPlayersItems() {
        List<Player> players = playerManager.getPlayersWaitingItems();
        if (!players.isEmpty()) {
            Map<String, ItemsForFirebase> playersItemsMap = new HashMap<>();
            for (Player player : players)
                playersItemsMap.put(player.getEmail(), EntityConverter.convertItems(player.getInventory().getItems()));
            serverDatabaseAPI.sendPlayersItems(playersItemsMap);
            playerManager.getPlayersWaitingItems().clear();
        }
    }

    private void updateInGameScoreOfPlayer() {
        Map<String, Integer> emailsScoreMap = new HashMap<>();
        for (Player player : PlayerManager.getInstance().getPlayers()) {
            player.updateLocalScore();
            Log.d(TAG, "updateInGameScoreOfPlayer: " + player.getEmail() + " " + player.getCurrentGameScore());
            emailsScoreMap.put(player.getEmail(), player.getCurrentGameScore());
        }

        serverDatabaseAPI.updatePlayersCurrentScore(emailsScoreMap);
    }

    private void sendUserPosition() {
        commonDatabaseAPI.sendUserPosition(EntityConverter.playerToPlayerForFirebase(PlayerManager.getInstance().getCurrentUser()));
    }

    private void sendPlayersStatus(){
        List<Player> players = playerManager.getPlayersWaitingStatusUpdate();
        if(!players.isEmpty()) {
            serverDatabaseAPI.sendPlayersStatus(EntityConverter.convertPlayerList(players));
            players.clear();
            Log.d(TAG,"players status sent");
        }
    }
}