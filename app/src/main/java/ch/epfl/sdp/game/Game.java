package ch.epfl.sdp.game;

import java.util.ArrayList;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.map.MapsActivity;

/**
 * Main model of the game, it is used for state changes and animations.
 */
public class Game implements Updatable, Drawable {
    public GameThread gameThread;
    private static ArrayList<Updatable> updatables;
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
            if(displayable.once()){
                MapsActivity.mapApi.getActivity().runOnUiThread(() ->  MapsActivity.mapApi.displayEntity(displayable));
            }else{
                displayables.add(displayable);
            }
        }
    }

    public static void removeFromDisplayList(Displayable displayable) {
        if (displayable != null) {
            displayables.remove(displayable);
            if(MapsActivity.mapApi.getActivity() != null) {
                MapsActivity.mapApi.getActivity().runOnUiThread(() -> MapsActivity.mapApi.unDisplayEntity(displayable));
            }
        }
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
        }
    }

    /**
     * Show the changes in the screen, will lead to animation
     */
    @Override
    public void draw() {
        for (Displayable displayable : displayables) {
            MapsActivity.mapApi.getActivity().runOnUiThread(() ->  MapsActivity.mapApi.displayEntity(displayable));
        }
    }
}
