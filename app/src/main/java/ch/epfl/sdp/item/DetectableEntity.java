package ch.epfl.sdp.item;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.map.GeoPoint;

public abstract class DetectableEntity extends InteractiveEntity implements Updatable {
    private boolean once;
    private boolean finalized = false;

    public DetectableEntity(EntityType entityType) {
        this(entityType, true);
    }

    public DetectableEntity(EntityType entityType, boolean once) {
        super(entityType);
        this.once = once;
    }

    public DetectableEntity(EntityType entityType, GeoPoint location, boolean once) {
        super(entityType, location);
        this.once = once;
    }

    public abstract void react(Player player);

    private boolean detectedBy(Player player) {
        return super.getLocation().distanceTo(player.getLocation()) - player.getAoeRadius() < 1;
    }

    // Makes it disappear from the game
    private void finalizeEntity() {
        Game.removeCurrentFromUpdateList();
        Game.removeFromDisplayList(this);
    }

    private void afterDetected(Player player) {
        react(player);
        if (once) {
            finalizeEntity();
            finalized = true;
        }
    }

    /**
     * Sorry it's more complicated due to codeclimate
     */
    @Override
    public void update() {
        int i = 0;
        while (i < PlayerManager.getPlayers().size() && !finalized) {
            if (detectedBy(PlayerManager.getPlayers().get(i))) {
                afterDetected(PlayerManager.getPlayers().get(i));
            }

            ++i;
        }
    }
}
