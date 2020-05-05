package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.Player;

public class Coin extends Item {
    private int value;

    public Coin(int value) {
        super(String.format("Coin of value %d", value), "Medium of exchange that allows a player to buy items in shops");
        this.value = value;
    }

    @Override
    public void useOn(Player player) {
        player.addMoney(value);
    }
    public Item clone() {
        return new Coin(value);
    }

    public int getValue() {
        return value;
    }

}
