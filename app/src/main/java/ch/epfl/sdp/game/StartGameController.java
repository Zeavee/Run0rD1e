package ch.epfl.sdp.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.artificial_intelligence.RandomEnemyGenerator;
import ch.epfl.sdp.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.database.firebase.entity.EntityConverter;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.CircleArea;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.item.Coin;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.item.ItemBoxManager;

import static android.content.ContentValues.TAG;

public interface StartGameController {
    /**
     * Implemented by Solo, Server and Client and used in GoogleLocationFinder,
     * After fetching the location of the CurrentUser (instead of the default 0, 0) from device for the first time we start the whole game
     * For Solo: It means populate the Enemies, itemboxes locally and start the gameThread
     * For Server: It means populate the Enemies and itemboxes on cloud firebase and start the gameThread
     * For Client: It means fetch the Enemies and itemboxes from cloud firebase and start the gameThread.
     */
    void start();

    /**
     * This method adds the player in the player manager
     *
     * @param playerManager the player manager we want to add the players in
     * @param players       the list of players we want to add
     */
    static void addPlayersInPlayerManager(PlayerManager playerManager, List<PlayerForFirebase> players) {
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
    static Area initGameArea() {
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
     * @param gameArea     the game area
     * @param enemyManager the enemy manager
     */
    static void initEnemies(Area gameArea, EnemyManager enemyManager) {
        // Enemy -------------------------------------------
        Area area = new UnboundedArea();
        RandomEnemyGenerator randomEnemyGenerator = new RandomEnemyGenerator(gameArea, area);
        randomEnemyGenerator.setEnemyCreationTime(1000);
        randomEnemyGenerator.setMaxEnemies(10);
        randomEnemyGenerator.setMinDistanceFromEnemies(100);
        randomEnemyGenerator.setMinDistanceFromPlayers(100);
        randomEnemyGenerator.generateEnemy(100);
        Enemy enemy = randomEnemyGenerator.getEnemies().get(0);
        SinusoidalMovement movement = new SinusoidalMovement(10, 0.1);
        enemy.setMovement(movement);
        enemyManager.updateEnemies(enemy);
        //  -------------------------------------------
    }

    /**
     * This method initializes the coins
     *
     * @param center the location around which we want to coins to appear
     */
    static void initCoins(GeoPoint center) {
        int amount = 10;
        ArrayList<Coin> coins = Coin.generateCoinsAroundLocation(center, amount);
        for (Coin c : coins) {
            Game.getInstance().addToDisplayList(c);
            Game.getInstance().addToUpdateList(c);
        }
    }

    /**
     * This method initializes the item boxes
     */
    static void initItemBoxes() {
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
}
