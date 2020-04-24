package ch.epfl.sdp.artificial_intelligence;

/**
 * Represents an entity in the game with the capability of moving autonomously during the game.
 */
public interface Movable {
    /**
     * This method should allow an entity to move autonomously during the game.
     * This method can be triggered by some kind of game event.
     */
    void move();

    /**
     * This method should return true if and only if the movable entity is actually moving in the
     * 2D plane during the game.
     *
     * @return True if and only if the movable entity is moving.
     */
    boolean isMoving();
}
