package ch.epfl.sdp.game;

import ch.epfl.sdp.artificial_intelligence.Updatable;

/**
 * Main model of the game, it is used for state changes and animations.
 */
public class Game implements Updatable, Drawable {
    private GameThread gameThread;

    public Game(){
        gameThread = new GameThread(this);
    }

    /**
     * Change the state of the game (ie. players life, items used, enemies movement, etc.)
     */
    @Override
    public void update() {

    }

    /**
     * Show the changes in the screen, will lead to animation
     */
    @Override
    public void draw() {

    }
}
