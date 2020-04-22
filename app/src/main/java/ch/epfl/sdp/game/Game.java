package ch.epfl.sdp.game;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Iterator;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.database.InitializeGame;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.map.MapsActivity;

/**
 * Main model of the game, it is used for state changes and animations.
 */
public class Game implements Updatable, Drawable {
    public GameThread gameThread;
    private static MapApi map;
    private InitializeGame initializeGame;
    private static ArrayList<Updatable> updatables;
    private static Iterator<Updatable> itUpdatable; // Necessary to be able to remove element while looping
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

    public Game() {
        gameThread = new GameThread(this);
        updatables = new ArrayList<>();
        displayables = new ArrayList<>();
    }


    public static void addToUpdateList(Updatable updatable) {
        updatables.add(updatable);
    }

    public static void removeFromUpdateList(Updatable updatable) {
        updatables.remove(updatable);
    }

    /**
     * Remove the current iterated element from the update list
     */
    public static void removeCurrentFromUpdateList() {
        itUpdatable.remove();
    }

    public static void addToDisplayList(Displayable displayable) {
        MapsActivity.mapApi.displayEntity(displayable);
        if (!displayable.once()) {
            displayables.add(displayable);
        }
    }

    public static void removeFromDisplayList(Displayable displayable) {
        MapsActivity.mapApi.unDisplayEntity(displayable);
        displayables.remove(displayable);
    }

    public static boolean updatablesContains(Updatable updatable){
        return updatables.contains(updatable);
    }

    public static boolean displayablesContains(Displayable displayable){
        return updatables.contains(displayable);
    }

    public ArrayList<Updatable> getUpdatables() {
        return updatables;
    }

    public ArrayList<Displayable> getDisplayables() {
        return displayables;
    }

    /**
     * Launches the game loop
     */
    public void initGame() {
        // It is not legal to start a terminated thread, we have create a new one
        if(gameThread.getState() == Thread.State.TERMINATED){
            gameThread = new GameThread(this);
        }

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
        itUpdatable = updatables.iterator();

        while (itUpdatable.hasNext()) {
            itUpdatable.next().update();
        }
    }

    /**
     * Show the changes in the screen, will lead to animation
     */
    @Override
    public void draw() {
        for (Displayable displayable : displayables) {
//<<<<<<< HEAD
//            if (displayable instanceof Item) {
//                if (displayable.isActive()) {
//                    map.getActivity().runOnUiThread(() -> map.displayEntity(displayable));
//                } else {
//                    map.getActivity().runOnUiThread(() -> map.unDisplayEntity(displayable));
//                    removeFromDisplayList(displayable);
//                }
//            } else {
//                map.getActivity().runOnUiThread(() -> map.displayEntity(displayable));
//            }
//=======
            MapsActivity.mapApi.displayEntity(displayable);
//>>>>>>> master
        }
    }
}
