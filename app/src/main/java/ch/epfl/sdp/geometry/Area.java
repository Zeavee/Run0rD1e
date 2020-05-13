package ch.epfl.sdp.geometry;

import ch.epfl.sdp.map.Displayable;

/**
 * Represents an area in the 2D plane.
 */
public abstract class Area implements Positionable, Displayable {
    protected GeoPoint center;

    public boolean isInside(GeoPoint geoPoint) {
        Vector vect = center.toVector().subtract(geoPoint.toVector());
        return isInside(vect);
    }

    protected abstract boolean isInside(Vector vector);

    public void setCenter(GeoPoint center) {
        this.center = center;
    }

    @Override
    public GeoPoint getLocation() {
        return center;
    }
}
