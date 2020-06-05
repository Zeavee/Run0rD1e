package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.GameThread;
import ch.epfl.sdp.game.Updatable;

/**
 * Represents an item with timed lasting effect.
 */
public abstract class TimedItem extends Item implements Updatable {
    private int counter;
    int countTime;
    private Player player;

    /**
     * Creates a timed item.
     *
     * @param name        The name of the timed item.
     * @param description The description of the timed item.
     * @param countTime   The time duration of the effect.
     */
    protected TimedItem(String name, String description, int countTime) {
        super(name, description);
        this.counter = countTime * GameThread.FPS;
        this.countTime = countTime;
    }

    /**
     * Add the timed item to the game, the count will begin.
     *
     * @param player A player that will begin to use the item.
     */
    public void useOn(Player player) {
        Game.getInstance().addToUpdateList(this);
        this.player = player;
    }

    /**
     * Remove the non lasting effects of the item after the time is over.
     *
     * @param player A player that will stop using the item.
     */
    public abstract void stopUsingOn(Player player);

    /**
     * Gets the remaining time.
     *
     * @return A value representing the remaining time in seconds.
     */
    public int getRemainingTime() {
        return counter / GameThread.FPS;
    }

    @Override
    public void update() {
        if (counter > 0) {
            --counter;
        } else {
            stopUsingOn(player);
            Game.getInstance().removeFromUpdateList(this);
        }
    }
}
