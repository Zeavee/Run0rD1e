package ch.epfl.sdp.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.artificial_intelligence.RandomEnemyGenerator;
import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.entityForFirebase.EntityConverter;
import ch.epfl.sdp.database.firebase.entityForFirebase.PlayerForFirebase;
import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.entity.ShelterArea;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.CircleArea;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Coin;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.item.ItemBoxManager;
import ch.epfl.sdp.utils.RandomGenerator;

import static android.content.ContentValues.TAG;

public abstract class StartGameController {
    private RandomEnemyGenerator randomEnemyGenerator;
    private RandomGenerator  randGen = new RandomGenerator();
    private static final int MAX_ENEMY = 10;
    private static final int MIN_DIST_FROM_ENEMIES = 100;
    private static final int MIN_DIST_FROM_PLAYERS = 100;
    private static final int MAX_QTY_INSIDE_ITEM_BOX = 5;
    private static final int NB_COINS = 20;
    private static final int NB_SHELTER_AREAS = 5;


    final CommonDatabaseAPI commonDatabaseAPI;

    StartGameController(CommonDatabaseAPI commonDatabaseAPI) {
        this.commonDatabaseAPI = commonDatabaseAPI;
    }

    /**
     * Implemented by Solo, Server and Client and used in GoogleLocationFinder,
     * After fetching the location of the CurrentUser (instead of the default 0, 0) from device for the first time we start the whole game
     * For Solo: It means populate the Enemies, itemboxes locally and start the gameThread
     * For Server: It means populate the Enemies and itemboxes on cloud firebase and start the gameThread
     * For Client: It means fetch the Enemies and itemboxes from cloud firebase and start the gameThread.
     */
    public abstract void start();

    /**
     * This method adds the player in the player manager
     *
     * @param playerManager the player manager we want to add the players in
     * @param players       the list of players we want to add
     */
    void addPlayersInPlayerManager(PlayerManager playerManager, List<PlayerForFirebase> players) {
        for (PlayerForFirebase playerForFirebase : players) {
            Player player = EntityConverter.playerForFirebaseToPlayer(playerForFirebase);
            if (!playerManager.getCurrentUser().getEmail().equals(player.getEmail())) {
                playerManager.addPlayer(player);
            }
            Log.d(TAG, "(Server) Getting Player: " + player);
        }
    }

    /**
     * This method initializes the game area
     *
     * @return the game area created
     */
    Area initGameArea() {
        //GameArea -----------------------------------------
        GeoPoint local = PlayerManager.getInstance().getCurrentUser().getLocation();
        Area gameArea = new CircleArea(3000, local);
         Game.getInstance().addToDisplayList(gameArea);
         Game.getInstance().addToUpdateList(gameArea);
         Game.getInstance().areaShrinker.setGameArea(gameArea);
        return gameArea;
    }

    /**
     * This method initializes the random enemy generator
     *
     * @param gameArea the game area
     */
    void createRandomEnemyGenerator(Area gameArea) {
        randomEnemyGenerator = new RandomEnemyGenerator(gameArea);
        randomEnemyGenerator.setMaxEnemies(MAX_ENEMY);
        randomEnemyGenerator.setMinDistanceFromEnemies(MIN_DIST_FROM_ENEMIES);
        randomEnemyGenerator.setMinDistanceFromPlayers(MIN_DIST_FROM_PLAYERS);
    }

    /**
     * This method generate an enemy
     *
     * @param enemyManager the enemy manager
     */
    void generateEnemy(EnemyManager enemyManager) {
        if(enemyManager.getEnemies().size() < MAX_ENEMY) {
            // generate new enemy
            Enemy enemy = randomEnemyGenerator.generateEnemy(100);
            if(enemy != null) {
                enemyManager.updateEnemies(enemy);
            }
        }
    }


    /**
     * Creates Coins, the shelterAreas as well as the items inside the area
     * @param gameArea
     */
    void initGameObjects(Area gameArea) {
        for (int i = 0; i < NB_COINS; i++) {
            Coin c = randGen.randomCoin(gameArea.randomLocation());
            Game.getInstance().addToDisplayList(c);
            Game.getInstance().addToUpdateList(c);
            ShelterArea s;
            if(i < NB_SHELTER_AREAS) {
                s = randGen.randomShelterArea(gameArea.randomLocation());
                Game.getInstance().addToDisplayList(s);
                Game.getInstance().addToUpdateList(s);
            }
        }
    }

    void updateGeneralScore() {
        Player currentPlayer = PlayerManager.getInstance().getCurrentUser();
        currentPlayer.setGeneralScore(currentPlayer.getGeneralScore() + currentPlayer.getCurrentGameScore());
        commonDatabaseAPI.updatePlayerGeneralScore(currentPlayer);
    }


    void initItemBox(Area gameArea) {
        ArrayList<Item> items = randGen.randomItemsList();
        for (Item i : items) {
            ItemBox itemBox = new ItemBox(gameArea.randomLocation());
            itemBox.putItems(i, randGen.getRand().nextInt(MAX_QTY_INSIDE_ITEM_BOX));
             Game.getInstance().addToDisplayList(itemBox);
             Game.getInstance().addToUpdateList(itemBox);
            ItemBoxManager.getInstance().addItemBox(itemBox); // puts in waiting list
        }
    }

}
