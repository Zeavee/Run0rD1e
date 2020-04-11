package ch.epfl.sdp.artificial_intelligence;

/**
 * Interface for updating the state of the game.
 */
public interface Updatable {
    /**
     * This method updates the state of an updatable object it is called by the game loop.
     */
    void update();
}
