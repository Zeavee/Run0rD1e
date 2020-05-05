package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.utils.RandomGenerator;

/**
 * Represents an entity with an active state and that is displayed on the map.
 */
public abstract class InteractiveEntity implements Displayable {
    private GeoPoint location;
    private boolean active;

    /**
     * Creates an interactive entity.
     */
    public InteractiveEntity() {
        this(RandomGenerator.randomLocationOnCircle(PlayerManager.getInstance().getCurrentUser().getLocation(), 1000) , false);
    }

    /**
     * Creates an interactive entity, by defining its location and if it is active.

     * @param location The location of the entity on the geodesic surface.
     */
    public InteractiveEntity(GeoPoint location) {
        this(location, false);
    }

    /**
     * Creates an interactive entity, by defining its location and if it is active.
     *
     * @param location   The location of the entity on the geodesic surface.
     * @param active     The flag that tells if the entity is active or not.
     */
    public InteractiveEntity(GeoPoint location, boolean active) {
        this.location = location;
        this.active = active;
    }

    /**
     * Return true if the entity is active.
     *
     * @return The flag that tells if the entity is active or not.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active flag.
     *
     * @param active The flag that tells if the entity is active or not.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public GeoPoint getLocation() {
        return location;
    }

    /**
     * Sets the location of the entity on the geodesic surface.
     * @param location The location on the geodesic surface.
     */
    public void setLocation(GeoPoint location){
        this.location = location;
    }

}
