package ch.epfl.sdp.geometry;

import ch.epfl.sdp.map.MapApi;

/**
 * Represents an unbounded area. Any point in the 2D plane is inside the area.
 */
public class UnboundedArea extends Area {
    @Override
    protected boolean isInside(Vector vector) {
        return true;
    }

    @Override
    public void displayOn(MapApi mapApi) {
        //We do not need to display it
    }
}
