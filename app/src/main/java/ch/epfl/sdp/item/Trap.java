package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.Displayable;

/**
 * A trap is an item a player can drop at his current localization and if another player walks by the trap, he will take damage
 */
public class Trap extends Item implements Updatable, Displayable {
    private GeoPoint trapPosition;
    private int damage;
    private int radius;
    private Player owner;

    /**
     * This is a constructor for traps
     * @param damage The damage the trap will inflict on the other player that walks by
     * @param radius The radius of the circle in which the other player needs to be in order for the trap to activate
     */
    public Trap(int damage, int radius) {
        super(String.format("Trap (%d)", damage), String.format("Deal %d health points damages to the player that walks on it", damage));
        this.damage = damage;
        this.radius = radius;
    }

    @Override
    public void useOn(Player player) {
        //This is called by the player that has the item, so getUser should return the correct player
        //The trick is that we need to save the reference to the player, since the update method will be called by the server and thus,
        //getUser would return the wrong player (the server)
        owner = PlayerManager.getInstance().getCurrentUser();
        trapPosition = owner.getLocation();
        Game.getInstance().addToUpdateList(this);
        if (owner == PlayerManager.getInstance().getCurrentUser()) {
            Game.getInstance().addToDisplayList(this);
        }
    }

    @Override
    public void update() {
        boolean hasSomeoneTakenDamage = false;
        for (Player p : PlayerManager.getInstance().getPlayers()) {
            if (p.getLocation().distanceTo(trapPosition) < radius && p != owner) {
                p.setHealthPoints(p.getHealthPoints() - damage);
                hasSomeoneTakenDamage = true;
            }
        }
        if (hasSomeoneTakenDamage) {
            Game.getInstance().removeFromUpdateList(this);
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
    public boolean isOnce() {
        return true;
    }
}