package ch.epfl.sdp.game;

import java.util.ArrayList;
import java.util.Iterator;

import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.map.MapsActivity;

/**
 * Main model of the game, it is used for state changes and animations.
 */
public class Game implements Updatable {
    public GameThread gameThread;
    private static MapApi map; // is it a good idea?
    private static ArrayList<Updatable> updatables;
    private static Iterator<Updatable> itUpdatable; // Necessary to be able to remove element while looping
    private static ArrayList<Displayable> displayables;

    /**
     * Instantiates a new game (uses mapApi by default. So for tests you need to
     * change the map before launching)
     */
    public Game() {
        gameThread = new GameThread(this);
        updatables = new ArrayList<>();
        displayables = new ArrayList<>();
    }

    /**
     * Add the given updatable entity to the game.
     *
     * @param updatable The updatable to be added.
     */
    public static void addToUpdateList(Updatable updatable) {
        updatables.add(updatable);
    }

    /**
     * Remove the given updatable entity from the game.
     * @param updatable The updatable to be removed.
     */
    public static void removeFromUpdateList(Updatable updatable) {
        updatables.remove(updatable);
    }

    /**
     * Remove the current iterated element from the update list.
     */
    public static void removeCurrentFromUpdateList() {
        itUpdatable.remove();
    }

    /**
     * Add the given displayable entity to the game. If the once flag of the displayable is true
     * display the entity one time and don't add to the list.
     * @param displayable The displayable to be added.
     */
    public static void addToDisplayList(Displayable displayable) {
        MapsActivity.mapApi.displayEntity(displayable);

        if (!displayable.isOnce()) {
            displayables.add(displayable);
        }
    }

    /**
     * Remove the given displayable entity from the game.
     * @param displayable The displayable to be removed.
     */
    public static void removeFromDisplayList(Displayable displayable) {
        MapsActivity.mapApi.unDisplayEntity(displayable);
        displayables.remove(displayable);
    }

    /**
     * Checks if the updatable is in the list of updatables.
     * @param updatable The updatable to check.
     * @return True if the updatable is in the list.
     */
    public static boolean updatablesContains(Updatable updatable){
        return updatables.contains(updatable);
    }

    /**
     * Checks if the displayable is in the list of displayables.
     * @param displayable The displayable to check.
     * @return True if the updatable is in the list.
     */
    public static boolean displayablesContains(Displayable displayable){
        return displayables.contains(displayable);
    }

    /**
     * Gets the list of updatables.
     * @return A list with all the updatables.
     */
    public ArrayList<Updatable> getUpdatables() {
        return updatables;
    }

    /**
     * Gets the list of displayables.
     * @return A list with all the displayables.
     */
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
    public void draw() {
        for (Displayable displayable : displayables) {
            MapsActivity.mapApi.displayEntity(displayable);
        }
    }
}
