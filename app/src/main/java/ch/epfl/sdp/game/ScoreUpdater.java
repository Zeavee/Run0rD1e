package ch.epfl.sdp.game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;

/**
 * This class is used to update the score of all the players
 */
public class ScoreUpdater {
    private ScheduledExecutorService exec;
    volatile private boolean isTerminated;

    public void setIsTerminated(boolean isTerminated)
    {
        this.isTerminated = isTerminated;
    }

    /**
     * This is the constructor of the class
     * It creates a new thread that will update the score every 10 seconds
     */
    public ScoreUpdater() {
        exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            if (isTerminated) {
                return;
            }
            updateLocalScoreOfPlayers();
        }, 10, 10, TimeUnit.SECONDS);
    }

    /**
     * This method ends the updating of the score
     * It also gives the bonus to the last players alive
     */
    public void destroy() {
        isTerminated = true;
        updateGeneralScoreOfPlayers();
    }

    /**
     * This method update the local score of all the player in the game (the rule is that every 10 seconds, a player gets 10 points
     * and if he walked more than 10 meters, he also gets 10 points
     */
    private void updateLocalScoreOfPlayers() {
        for (Player player : PlayerManager.getInstance().getPlayers()) {
            player.updateLocalScore();
        }
    }

    /**
     * This method updates the general score of all the players in the game at the end of the game.
     * All the players get their local score added to the general score and if they are alive, they get 50 bonus points
     */
    private void updateGeneralScoreOfPlayers() {
        for (Player player : PlayerManager.getInstance().getPlayers()) {
            if (player.isAlive()) {
                player.currentGameScore += 50;
            }
            player.generalScore += player.currentGameScore;
            player.currentGameScore = 0;
        }
    }
}
