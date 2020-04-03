package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.logic.RandomGenerator;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

public abstract class InteractiveEntity implements Displayable {
    private EntityType entityType;
    private GeoPoint location;
    private boolean active;

    public InteractiveEntity(EntityType entityType) {
        this(entityType,  RandomGenerator.randomLocationOnCircle(MapsActivity.mapApi.getCurrentLocation(), 100) , false);
    }

    public InteractiveEntity(EntityType entityType, GeoPoint location) {
        this(entityType, location, false);
    }

    public InteractiveEntity(EntityType entityType, GeoPoint location, boolean active) {
        this.entityType = entityType;
        this.location = location;
        this.active = active;
    }

    @Override
    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location){
        this.location = location;
    }

    @Override
    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }
}
