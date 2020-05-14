package ch.epfl.sdp.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.GoogleMapApi;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.map.Renderer;

/**
 * Main model of the game, it is used for state changes and animations.
 */
public class Game implements Updatable {
    private MapApi mapApi;
    private GameThread gameThread;
    private ArrayList<Updatable> updatables;
    private Iterator<Updatable> itUpdatable; // Necessary to be able to remove element while looping
    private ArrayList<Displayable> displayables;
    private Renderer renderer;
    private ScoreUpdater scoreUpdater;

    private static Game instance = new Game();

    /**
     * Gets one and only instance of the game.
     */
    public static Game getInstance() {
        return instance;
    }

    /**
     * Instantiates a new game (uses mapApi by default. So for tests you need to
     * change the map before launching)
     */
    private Game() {
        mapApi = null;
        gameThread = new GameThread(this);
        updatables = new ArrayList<>();
        displayables = new ArrayList<>();
        scoreUpdater = new ScoreUpdater();
    }

    /**
     * This permits to set the MapApi the game will use
     *
     * @param mapApi the MapApi the game will use
     */
    public void setMapApi(MapApi mapApi) {
        this.mapApi = mapApi;
    }

    /**
     * This permits to set the Renderer the game will use
     *
     * @param renderer the Renderer the game will use
     */
    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Returns the MapApi the game is using.
     *
     * @return the MapApi the game is using.
     */
    public MapApi getMapApi() {
        return mapApi;
    }

    /**
     * Returns true if the game is running.
     *
     * @return true if the game is running.
     */
    public boolean isRunning() {
        return gameThread.isRunning();
    }

    /**
     * Clears the game (ie. update and displayable list)
     */
    public void clearGame() {
        updatables.clear();
        displayables.clear();
    }

    /**
     * Add the given updatable entity to the game.
     *
     * @param updatable The updatable to be added.
     */
    private ReentrantLock lock = new ReentrantLock();

    public void addToUpdateList(Updatable updatable) {
        lock.lock();
        updatables.add(updatable);
        lock.unlock();
    }

    /**
     * Remove the given updatable entity from the game.
     *
     * @param updatable The updatable to be removed.
     */
    public void removeFromUpdateList(Updatable updatable) {
        updatables.remove(updatable);
    }

    /**
     * Remove the current iterated element from the update list.
     */
    public void removeCurrentFromUpdateList() {
        itUpdatable.remove();
    }

    /**
     * Add the given displayable entity to the game. If the once flag of the displayable is true
     * display the entity one time and don't add to the list.
     *
     * @param displayable The displayable to be added.
     */
    public void addToDisplayList(Displayable displayable) {
        displayables.add(displayable);
    }

    /**
     * Remove the given displayable entity from the game.
     *
     * @param displayable The displayable to be removed.
     */
    public void removeFromDisplayList(Displayable displayable) {
        renderer.unDisplay(displayable);
        displayables.remove(displayable);
    }

    /**
     * Checks if the updatable is in the list of updatables.
     *
     * @param updatable The updatable to check.
     * @return True if the updatable is in the list.
     */
    public boolean updatablesContains(Updatable updatable) {
        return updatables.contains(updatable);
    }

    /**
     * Checks if the displayable is in the list of displayables.
     *
     * @param displayable The displayable to check.
     * @return True if the updatable is in the list.
     */
    public boolean displayablesContains(Displayable displayable) {
        return displayables.contains(displayable);
    }

    /**
     * Gets the list of updatables.
     *
     * @return A list with all the updatables.
     */
    public ArrayList<Updatable> getUpdatables() {
        return updatables;
    }

    /**
     * Gets the list of displayables.
     *
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
        scoreUpdater.setIsTerminated(false);
        if (gameThread.getState() == Thread.State.TERMINATED) {
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
        // This line is needed, don't delete it, or else infinite while loop.
        if(gameThread.getState() == Thread.State.NEW) {
            return;
        }

        while (gameThread.getState() != Thread.State.TERMINATED) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        scoreUpdater.destroy();
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
        renderer.display(displayables);
    }

    public boolean getGameThreadExceptionFlag(){
        return gameThread.getExceptionFlag();
    }
}
