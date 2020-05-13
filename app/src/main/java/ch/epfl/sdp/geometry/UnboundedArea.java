package ch.epfl.sdp.geometry;

import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.utils.RandomGenerator;

/**
 * Represents an unbounded area. Any point in the 2D plane is inside the area.
 */
public class UnboundedArea extends Area {

    public UnboundedArea() {
        super(new RandomGenerator().randomGeoPoint());
    }

    @Override
    public void shrink(double factor) {

    }

    @Override
    public Area getShrinkTransition() {
        return this;
    }

    @Override
    protected boolean isInside(Vector vector) {
        return true;
    }

    @Override
    public GeoPoint randomLocation() {
        return new RandomGenerator().randomGeoPoint();
    }

    @Override
    public void finishShrink() {

    }

    @Override
    public void displayOn(MapApi mapApi) {
        //We do not need to display it
    }
}
