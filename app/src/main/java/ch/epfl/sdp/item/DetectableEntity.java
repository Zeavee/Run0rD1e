package ch.epfl.sdp.item;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.map.GeoPoint;

public abstract class DetectableEntity extends InteractiveEntity implements Updatable {
    private boolean once;

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

    @Override
    public void update() {
        System.out.println("lat: " + getLocation().getLatitude() + " lon: " + getLocation().getLongitude());

        for (Player player: PlayerManager.getPlayers()) {
            if (super.getLocation().distanceTo(player.getLocation()) - player.getAoeRadius() < 0) {
                react(player);
                if(once){
                    //super.setActive(false);
                    // Make it disappear from the game
                    Game.removeFromUpdateList(this);
                    Game.removeFromDisplayList(this);
                }
            }
        }
    }
}
