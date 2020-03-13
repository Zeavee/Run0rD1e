package ch.epfl.sdp.game;

import java.util.ArrayList;

import ch.epfl.sdp.Displayable;
import ch.epfl.sdp.MapApi;
import ch.epfl.sdp.artificial_intelligence.Updatable;

import static ch.epfl.sdp.MapsActivity.mapApi;

/**
 * Main model of the game, it is used for state changes and animations.
 */
public class Game implements Updatable, Drawable {
    private GameThread gameThread;
    private MapApi map;
    private ArrayList<Updatable> updatables;
    private ArrayList<Displayable> displayables;

    /**
     * Instantiates a new game (uses mapApi by default. So for tests you need to
     * change the map before launching)
     */
    public Game(){
        this.map = mapApi;
        updatables = new ArrayList<>();
        displayables = new ArrayList<>();
        gameThread = new GameThread(this);
    }

    /**
     * Launches the game
     */
    public void initGame() {
        gameThread.setRunning(true);
        gameThread.start();
    }

    /**
     * Kill the game
     */
    public void destroyGame() {
        boolean retry = true;
        while (retry) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                retry = false;
            }
        }
    }

    public void setMapApi(MapApi mapApi) {
        if (mapApi != null) {
            this.map = mapApi;
        }
    }

    public void addToUpdateList(Updatable updatable) {
        if (updatable != null) {
            updatables.add(updatable);
        }
    }

    public void removeFromUpdateList(Updatable updatable) {
        if (updatable != null) {
            updatables.remove(updatable);
        }
    }

    public void addToDisplayList(Displayable displayable) {
        if (displayable != null) {
            displayables.add(displayable);
        }
    }

    public void removeFromDisplayList(Displayable displayable) {
        if (displayable != null) {
            displayables.remove(displayable);
        }
    }

    /**
     * Change the state of the game (ie. players life, items used, enemies movement, etc.)
     */
    @Override
    public void update() {
        for (Updatable updatable : updatables) {
            updatable.update();
        }
    }

    /**
     * Show the changes in the screen, will lead to animation
     */
    @Override
    public void draw() {
        for (Displayable displayable : displayables) {
            map.displayEntity(displayable);
        }
    }
}
