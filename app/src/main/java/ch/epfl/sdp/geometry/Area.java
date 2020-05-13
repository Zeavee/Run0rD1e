package ch.epfl.sdp.geometry;

import ch.epfl.sdp.map.Displayable;

/**
 * Represents an area in the 2D plane.
 */
public abstract class Area implements Positionable, Displayable {
    protected GeoPoint center;
    protected boolean isShrinking;

    public Area(GeoPoint center) {
        this.center = center;
    }

    public boolean isInside(GeoPoint geoPoint) {
        Vector vec = center.toVector().subtract(geoPoint.toVector());
        return isInside(vec);
    }

    /**
     * This method find a smaller GameArea that fits entirely inside the current GameArea
     *
     * @param factor it is a number we multiply with the current GameArea's size to get the new size
     * @return A random GameArea inside the current one
     */
    public abstract Area shrink(double factor);

    protected abstract boolean isInside(Vector vector);

    @Override
    public GeoPoint getLocation() {
        return center;
    }

    public abstract GeoPoint randomLocation();
}
