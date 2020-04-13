package ch.epfl.sdp.item;

import ch.epfl.sdp.artificial_intelligence.GenPoint;
import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;

public class Trap extends Item implements Updatable {
    private GenPoint trapPosition;
    private int damage;
    private int radius;
    private Player owner;

    public Trap(int damage, int radius) {
        super(String.format("Trap (%f)", damage), String.format("Deal %f health points damages to the player that walks on it", damage));
        this.damage = damage;
        this.radius = radius;
    }

    @Override
    public Item createCopy() {
        return new Trap(damage, radius);
    }

    @Override
    public void use() {
        //This is called by the player that has the item, so getUser should return the correct player
        //The trick is that we need to save the reference to the player, since the update method will be called by the server and thus,
        //getUser would return the wrong player (the server)
        owner = PlayerManager.getUser();
        trapPosition = owner.getPosition();
        Game.addToUpdateList(this);
    }

    @Override
    public void update() {
        boolean hasSomeoneTakenDamage = false;
        for (Player p : PlayerManager.getPlayers()) {
            if (p.position.distanceFrom(trapPosition) < radius && p != owner) {
                p.setHealthPoints(p.getHealthPoints() - damage);
                hasSomeoneTakenDamage = true;
            }
        }
        if (hasSomeoneTakenDamage) {
            Game.removeFromUpdateList(this);
        }
    }
}
