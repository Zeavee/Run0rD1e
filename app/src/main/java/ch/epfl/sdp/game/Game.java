package ch.epfl.sdp.game;

import com.google.common.collect.Maps;

import java.util.ArrayList;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.database.InitializeGame;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.map.MapsActivity;

/**
 * Main model of the game, it is used for state changes and animations.
 */
public class Game implements Updatable, Drawable {
    public GameThread gameThread;
    private MapApi map;
    private InitializeGame initializeGame;
    private static ArrayList<Updatable> updatables;
    private static ArrayList<Displayable> displayables;

    /**
     * Instantiates a new game (uses mapApi by default. So for tests you need to
     * change the map before launching)
     */
    public Game(MapApi mapApi, InitializeGame initializeGame) {
        // Need to test if it is null
        this.map = mapApi;
        this.initializeGame = initializeGame;
        updatables = new ArrayList<>();
        displayables = new ArrayList<>();
        gameThread = new GameThread(this);
    }

    public static void addToUpdateList(Updatable updatable) {
        if (updatable != null) {
            updatables.add(updatable);
        }
    }

    public static void removeFromUpdateList(Updatable updatable) {
        if (updatable != null) {
            updatables.remove(updatable);
        }
    }

    public static void addToDisplayList(Displayable displayable) {
        if (displayable != null) {
            displayables.add(displayable);
        }
    }

    public static void removeFromDisplayList(Displayable displayable) {
        if (displayable != null) {
            displayables.remove(displayable);
        }
    }

    public ArrayList<Updatable> getUpdatables() {
        return updatables;
    }

    public ArrayList<Displayable> getDisplayables() {
        return displayables;
    }

    public void setMapApi(MapApi mapApi) {
        if (mapApi != null) {
            this.map = mapApi;
        }
    }

    /**
     * Launches the game
     */
    public void initGame() {
        if (gameThread.getState() == Thread.State.NEW) {
            gameThread.setRunning(true);
            gameThread.start();
        }
    }

    public void setEnvironment() {
        initializeGame.setGameEnvironment();

//        this.updatables.add(PlayerManager.getInstance());
    }

    /**
     * Kill the game
     */
    public void destroyGame() {
        while (gameThread.getState() != Thread.State.TERMINATED) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Change the state of the game (ie. players life, items used, enemies movement, etc.)
     */
    @Override
    public void update() {
        for (Updatable updatable : updatables) {
            updatable.update();
            System.out.println("update Game");
        }
    }

    /**
     * Show the changes in the screen, will lead to animation
     */
    @Override
    public void draw() {
        for (Displayable displayable : displayables) {
            map.getActivity().runOnUiThread(() -> map.displayEntity(displayable));
        }
    }
}
