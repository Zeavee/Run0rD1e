package ch.epfl.sdp.geometry;

import ch.epfl.sdp.entity.PlayerManager;

/**
 * Represents an area on the 2D plane that has a position.
 */
public class LocalArea implements Area, Positionable {
    private Area bounds;
    private CartesianPoint position;

    /**
     * Creates an area with a position.
     *
     * @param area     The area to on the 2D plane.
     * @param position The position where the area is.
     */
    public LocalArea(Area area, CartesianPoint position) {
        this.bounds = area;
        if (position != null) {
            this.position = position;
        }
    }

    /**
     * Sets the area.
     *
     * @param area The area to be set.
     */
    public void setArea(Area area) {
        this.bounds = area;
    }

    @Override
    public boolean isInside(CartesianPoint cartesianPoint) {
        Vector vect = position.toVector().subtract(cartesianPoint.toVector());
        CartesianPoint origin = new CartesianPoint(0, 0);
        return bounds.isInside(origin.asOriginTo(vect));
    }

    /**
     * Gets the location.
     *
     * @return The location of the localArea on the geodesic surface.
     */
    public GeoPoint getLocation() {
        return PointConverter.cartesianPointToGeoPoint(position, PlayerManager.getInstance().getCurrentUser().getLocation());
    }

    @Override
    public CartesianPoint getPosition() {
        return position;
    }

    /**
     * Sets the position of the area.
     *
     * @param position The position in the 2D plane.
     */
    public void setPosition(CartesianPoint position) {
        this.position = position;
    }
}
