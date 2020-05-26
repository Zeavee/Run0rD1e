package ch.epfl.sdp.game;

import android.util.Log;

import java.util.ArrayList;

import ch.epfl.sdp.artificial_intelligence.RandomEnemyGenerator;
import ch.epfl.sdp.artificial_intelligence.SinusoidalMovement;
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

/**
 *  Control the whole game lifecycle of the solo mode
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
        if(!gameStarted) {
            gameStarted = true;

            previousLocation = currentUser.getLocation();

            // init the environment
            gameArea = StartGameController.initGameArea();
            StartGameController.initItemBoxes();
            StartGameController.initEnemies(gameArea, EnemyManager.getInstance());
            StartGameController.initCoins(currentUser.getLocation());

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
        if(!gameEnd && currentUser.getHealthPoints() <= 0) {
            currentUser.setGeneralScore(currentUser.getGeneralScore() + currentUser.getCurrentGameScore());
            gameEnd = true;
        }
    }

    public boolean isGameEnd() {
        return gameEnd;
    }
}
