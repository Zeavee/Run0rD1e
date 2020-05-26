package ch.epfl.sdp.game;

import android.util.Log;

import java.util.ArrayList;

import ch.epfl.sdp.artificial_intelligence.RandomEnemyGenerator;
import ch.epfl.sdp.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.CircleArea;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.item.Coin;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.ItemBox;

/**
 * Control the whole game lifecycle of the solo mode
 */
public class Solo implements StartGameController, Updatable {
    private Player currentUser = PlayerManager.getInstance().getCurrentUser();
    private int counter = 0;
    private Area gameArea;
    private GeoPoint previousLocation;
    private boolean gameStarted;
    private boolean gameEnd;

    public Solo() {
        this.gameStarted = false;
        this.gameEnd = false;
    }

    @Override
    public void start() {
        if (!gameStarted) {
            gameStarted = true;

            previousLocation = currentUser.getLocation();

            // init the environment
            initGameArea();
            initItemBoxes();
            initEnemies();
            initCoins();

            // start the Game thread
            Game.getInstance().addToUpdateList(this);
            Game.getInstance().initGame();
        }
    }

    @Override
    public void update() {
        checkGameEnd();

        // Update the distanceTravelled in 2 seconds
        if (counter % (2 * GameThread.FPS) == 0) {
            updateDistanceTravelled();
        }

        // Update the current game score in 10 seconds
        if (counter % (10 * GameThread.FPS) == 0) {
            updateIngameScore();

            // reset tht counter to avoid overflow
            counter = 0;
        }

        counter++;

    }

    private void initGameArea() {
        //GameArea -----------------------------------------
        GeoPoint local = PlayerManager.getInstance().getCurrentUser().getLocation();
        gameArea = new CircleArea(3000, local);
        Game.getInstance().addToDisplayList(gameArea);
        Game.getInstance().addToUpdateList(gameArea);
        Game.getInstance().areaShrinker.setGameArea(gameArea);
    }

    private void initEnemies() {
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
        Game.getInstance().addToDisplayList(enemy);
        Game.getInstance().addToUpdateList(enemy);
        //  -------------------------------------------
    }

    private void initCoins() {
        int amount = 10;
        ArrayList<Coin> coins = Coin.generateCoinsAroundLocation(currentUser.getLocation(), amount);
        for (Coin c : coins) {
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
        //  -------------------------------------------
    }

    private void updateDistanceTravelled() {
        Log.d("solo", "updateDistanceTravelled: previous Location" + "Longitude: " + previousLocation.getLongitude() + " Latitude: " + previousLocation.getLatitude());
        GeoPoint currentLocation = currentUser.getLocation();
        double traveledDistance = currentLocation.distanceTo(previousLocation);
        previousLocation = currentLocation;
        Log.d("solo", "updateDistanceTravelled: updated Location " + "Longitude: " + currentLocation.getLongitude() + " Latitude: " + currentLocation.getLatitude());
        Log.d("solo", "updateDistanceTravelled: traveled distance " + traveledDistance);

        currentUser.updateDistanceTraveled(traveledDistance);
    }

    private void updateIngameScore() {
        Log.d("solo", "update current game score: before updating score " + currentUser.getCurrentGameScore());
        currentUser.updateLocalScore();
        Log.d("solo", "update current game score: after updating score " + currentUser.getCurrentGameScore());
    }

    private void checkGameEnd() {
        if (!gameEnd && currentUser.getHealthPoints() <= 0) {
            currentUser.setGeneralScore(currentUser.getGeneralScore() + currentUser.getCurrentGameScore());
            gameEnd = true;
        }
    }

    public boolean isGameEnd() {
        return gameEnd;
    }
}
