package ch.epfl.sdp.item;

import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.GameThread;

/**
 * Represents an item with timed lasting effect.
 */
public abstract class TimedItem extends Item implements Updatable {
    protected int counter;

    /**
     * Creates a timed item.
     *
     * @param name        The name of the timed item.
     * @param description The description of the timed item.
     * @param countTime   The time duration of the effect.
     */
    public TimedItem(String name, String description, int countTime) {
        super(name, description);
        counter = countTime*GameThread.FPS;
    }

    /**
     * Add the timed item to the game, the count will begin.
     */
    public void use(){
        Game.addToUpdateList(this);
    }

    /**
     * Remove the non lasting effects of the item after the time is over.
     */
    public abstract void stopUsing();

    /**
     * Gets the remaining time.
     * @return A value representing the remaining time in seconds.
     */
    public int getRemainingTime() {
        return counter / GameThread.FPS;
    }

    @Override
    public void update() {
        if(counter > 0){
            --counter;
        }else{
            stopUsing();
            Game.removeFromUpdateList(this);
        }
    }
}
