package ch.epfl.sdp.geometry;

/**
 * Represents an area on the 2D plane that has a position.
 */
public class LocalArea implements Positionable {
    private Area area;
    private GeoPoint position;

    public LocalArea(Area area, GeoPoint geoPoint) {
        this.area = area;
        position = geoPoint;
    }

    /**
     * Sets the area.
     *
     * @param area The area to be set.
     */
    public void setArea(Area area) {
        this.area = area;
    }

    public boolean isInside(GeoPoint geoPoint) {
        Vector vect = position.toVector().subtract(geoPoint.toVector());
        return area.isInside(vect);
    }

    @Override
    public GeoPoint getLocation() {
        return position;
    }

    public void setPosition(GeoPoint position) {
        this.position = position;
    }
}
