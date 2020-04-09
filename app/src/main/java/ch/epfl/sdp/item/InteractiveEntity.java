package ch.epfl.sdp.item;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.logic.RandomGenerator;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

public abstract class InteractiveEntity implements Displayable {
    private EntityType entityType;
    private GeoPoint location;
    private boolean once;

    public InteractiveEntity(EntityType entityType) {
        this(entityType,  RandomGenerator.randomLocationOnCircle(MapsActivity.mapApi.getCurrentLocation(), 1000) , false);
    }

    public InteractiveEntity(EntityType entityType, GeoPoint location) {
        this(entityType, location, false);
    }

    public InteractiveEntity(EntityType entityType, GeoPoint location, boolean once) {
        this.entityType = entityType;
        this.location = location;
        this.once = once;
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
    public boolean once() {
        return once;
    }

    /*public void setActive(boolean active){
        this.active = active;
    }*/
}
