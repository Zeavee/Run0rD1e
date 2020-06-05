package ch.epfl.sdp.game;

import android.util.Log;

import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.entities.enemy.EnemyManager;
import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.GeoPoint;

/**
 * Control the whole game lifecycle of the solo mode
 */
public class Solo extends StartGameController implements Updatable {
    private final Player currentUser = PlayerManager.getInstance().getCurrentUser();
    private int counter = 0;
    private GeoPoint previousLocation;
    private boolean gameStarted;
    private boolean gameEnd;

    /**
     * The constructor for the Solo.
     */
    public Solo(CommonDatabaseAPI commonDatabaseAPI) {
        super(commonDatabaseAPI);
        this.gameStarted = false;
        this.gameEnd = false;
    }

    @Override
    public void start() {
        if (!gameStarted) {
            gameStarted = true;

            previousLocation = currentUser.getLocation();

            // init the environment
            Area gameArea = initGameArea();
            createRandomEnemyGenerator(gameArea);
            generateEnemy(EnemyManager.getInstance());
            initGameObjects(gameArea);
            // start the Game thread
            Game.getInstance().addToUpdateList(this);
            Game.getInstance().initGame();

            PlayerManager.getInstance().setIsServer(true);
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
        }

        // generate more enemy in 30 seconds
        if(counter % (30 * GameThread.FPS) == 0) {
            generateEnemy(EnemyManager.getInstance());

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
        if (!gameEnd && currentUser.status.getHealthPoints() <= 0) {
            currentUser.setGeneralScore(currentUser.getGeneralScore() + currentUser.getCurrentGameScore());
            gameEnd = true;
            commonDatabaseAPI.updatePlayerGeneralScore(currentUser);
        }
    }

    /**
     * Tells if the game is finished
     *
     * @return a boolean that tells if the game is finished
     */
    public boolean isGameEnd() {
        return gameEnd;
    }
}
