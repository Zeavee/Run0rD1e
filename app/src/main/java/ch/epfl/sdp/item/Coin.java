package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.Entity;
import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.PlayerManager;

public class Coin extends Item {
    private int value;

    public Coin(int value) {
        super(String.format("Coin of value %d", value), "Medium of exchange that allows a player to buy items in shops");
        this.value = value;
    }

    @Override
    public Item clone() {
        return new Coin(value);
    }

    @Override
    public void use() {
        PlayerManager.getCurrentUser().addMoney(value);
    }

    public int getValue() {
        return value;
    }

    /**
     * Method to get the type of the object we want to display
     *
     * @return an EntityType which is an enum of types
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.COIN;
    }
}
