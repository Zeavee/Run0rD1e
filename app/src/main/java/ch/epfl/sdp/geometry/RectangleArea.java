package ch.epfl.sdp.geometry;

import ch.epfl.sdp.map.MapApi;

/**
 * Represents a rectangle area.
 */
public class RectangleArea extends Area {
    private final float halfHeight;
    private final float halfWidth;

    /**
     * Creates a rectangle area with the given height and width.
     *
     * @param height The height of the rectangle.
     * @param width  The width of the rectangle.
     */
    public RectangleArea(float height, float width, GeoPoint center) {
        super(center);
        this.halfHeight = height / 2;
        this.halfWidth = width / 2;
    }

    /**
     * Gets the height of the rectangle.
     * @return A value representing the height of the rectangle.
     */
    public float getHeight() {
        return 2 * halfHeight;
    }

    /**
     * Gets the width of the rectangle.
     * @return A value representing the width of the rectangle.
     */
    public float getWidth() {
        return 2 * halfWidth;
    }

    @Override
    public RectangleArea shrink(double factor) {
        //TODO implement
        return null;
    }

    @Override
    protected boolean isInside(Vector vector) {
        return vector.x() < halfWidth && vector.x() > -halfWidth && vector.y() < halfHeight && vector.y() > -halfHeight;
    }

    @Override
    public GeoPoint randomLocation() {
        //TODO implement
        return null;
    }

    @Override
    public void displayOn(MapApi mapApi) {
        //TODO implement
    }
}