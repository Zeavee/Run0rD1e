package ch.epfl.sdp.items;

import ch.epfl.sdp.entities.player.Player;

/**
 * Represent a game item, which can have an effect on the game.
 */
public abstract class Item implements Cloneable {
    private final String name;
    private final String description;

    /**
     * Creates a game item.
     *
     * @param name        The name of the item.
     * @param description The description of the item.
     */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public abstract Item clone();

    /**
     * Gets the name of the item.
     *
     * @return A String representing the name of the item.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the description of the item.
     *
     * @return A string representing the description of the item.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * This method should modify the state of the player or game.
     *
     * @param player A player to in which we use the item
     */
    public abstract void useOn(Player player);

    /**
     * Gets the value (price in coins) of the item
     */
    public abstract double getValue();
}
