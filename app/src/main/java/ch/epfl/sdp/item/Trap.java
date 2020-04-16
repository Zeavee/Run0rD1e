package ch.epfl.sdp.item;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.GeoPoint;

public class Trap extends Item implements Updatable, Displayable {
    private GeoPoint trapPosition;
    private int damage;
    private int radius;
    private Player owner;

    public Trap(int damage, int radius) {
        super(String.format("Trap (%d)", damage), String.format("Deal %d health points damages to the player that walks on it", damage));
        this.damage = damage;
        this.radius = radius;
    }

    @Override
    public void use() {
        //This is called by the player that has the item, so getUser should return the correct player
        //The trick is that we need to save the reference to the player, since the update method will be called by the server and thus,
        //getUser would return the wrong player (the server)
        owner = PlayerManager.getUser();
        trapPosition = owner.getLocation();
        Game.addToUpdateList(this);
        if (owner == PlayerManager.getUser()) {
            Game.addToDisplayList(this);
        }
    }

    @Override
    public void update() {
        boolean hasSomeoneTakenDamage = false;
        for (Player p : PlayerManager.getPlayers()) {
            if (p.getLocation().distanceTo(trapPosition) < radius && p != owner) {
                p.setHealthPoints(p.getHealthPoints() - damage);
                hasSomeoneTakenDamage = true;
            }
        }
        if (hasSomeoneTakenDamage) {
            Game.removeFromUpdateList(this);
        }
    }

    @Override
    public GeoPoint getLocation() {
        return trapPosition;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.TRAP;
    }

    @Override
    public boolean once() {
        return true;
    }
}