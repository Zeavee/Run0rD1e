package ch.epfl.sdp.game;

import ch.epfl.sdp.map.Displayable;

/**
 * Interface for updating the state of the game.
 */
public interface Updatable {
    /**
     * This method updates the state of an updatable object when it is called by the game loop.
     */
    void update();
}
