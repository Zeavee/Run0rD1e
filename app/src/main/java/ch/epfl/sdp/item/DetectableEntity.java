package ch.epfl.sdp.item;

import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;

/**
 * Represents a game entity that can react to a detection.
 */
public abstract class DetectableEntity extends InteractiveEntity implements Updatable {
    /**
     * If true the entity only reacts once, after that the entity disappear from the game.
     */
    private boolean once;
    /**
     * If true then the entity has been destroyed and disappeared from the game.
     */
    private boolean detroyed = false;

    /**
     * Creates a detectable entity.
     *
     * @param entityType The type of the entity.
     */
    public DetectableEntity(EntityType entityType) {
        this(entityType, true);
    }

    /**
     * Creates a detectable entity.
     * @param entityType The type of the entity.
     * @param once If true the entity only reacts once, after that the entity disappear from the game.
     */
    public DetectableEntity(EntityType entityType, boolean once) {
        super(entityType);
        this.once = once;
    }

    /**
     *
     * @param entityType entityType The type of the entity.
     * @param location The location of the entity on the geodesic surface.
     * @param once If true the entity only reacts once, after that the entity disappear from the game.
     */
    public DetectableEntity(EntityType entityType, GeoPoint location, boolean once) {
        super(entityType, location);
        this.once = once;
    }

    /**
     * Reacts to a detection.
     * @param player The player that has detected the entity.
     */
    public abstract void react(Player player);

    /**
     * Return true if the player has detected the entity.
     * @param player The player to check for detection.
     * @return True if the player has detected the entity.
     */
    private boolean detectedBy(Player player){
        return super.getLocation().distanceTo(player.getLocation()) - player.getAoeRadius() < 1;
    }

    /**
     * Remove the entity from the game.
     */
    private void destroy(){
        Game.removeCurrentFromUpdateList();
        Game.removeFromDisplayList(this);
        detroyed = true;
    }

    /**
     * React to the given player and destroys itself if the flag once it set to true.
     * @param player The player that has detected the entity.
     */
    private void afterDetected(Player player){
        react(player);
        if(once) {
            destroy();
        }
    }

    @Override
    public boolean isOnce() {
        return once;
    }

    @Override
    public void update() {
        int i = 0;
        while (i < PlayerManager.getPlayers().size() && !detroyed) {
            if (detectedBy(PlayerManager.getPlayers().get(i))) {
                afterDetected(PlayerManager.getPlayers().get(i));
            }

            ++i;
        }
    }
}
