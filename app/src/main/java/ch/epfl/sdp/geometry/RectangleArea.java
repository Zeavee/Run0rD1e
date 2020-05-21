package ch.epfl.sdp.geometry;

import androidx.annotation.NonNull;

import java.util.Random;

import ch.epfl.sdp.map.MapApi;

/**
 * Represents a rectangle area.
 */
public class RectangleArea extends Area {
    private double halfHeight;
    private double halfWidth;

    private double newHalfHeight;
    private double newHalfWidth;

    private double oldHalfHeight;
    private double oldHalfWidth;

    /**
     * Creates a rectangle area with the given height and width.
     *
     * @param height The height of the rectangle.
     * @param width  The width of the rectangle.
     */
    public RectangleArea(double height, double width, GeoPoint center) {
        super(center);
        this.halfHeight = height / 2;
        this.halfWidth = width / 2;
    }

    /**
     * Gets the height of the rectangle.
     *
     * @return A value representing the height of the rectangle.
     */
    public double getHeight() {
        return 2 * halfHeight;
    }

    /**
     * Gets the width of the rectangle.
     *
     * @return A value representing the width of the rectangle.
     */
    public double getWidth() {
        return 2 * halfWidth;
    }

    @Override
    public void shrink(double factor) {
        if (factor < 0 || factor > 1) {
            return;
        }
        oldCenter = center;
        newCenter = new RectangleArea(2 * halfHeight * (1 - factor), 2 * halfWidth * (1 - factor), center).randomLocation();

        oldHalfHeight = halfHeight;
        oldHalfWidth = halfWidth;

        newHalfHeight = halfHeight * (float) factor;
        newHalfWidth = halfWidth * (float) factor;

        isShrinking = true;
    }

    @Override
    public void setShrinkTransition() {
        if (time > finalTime || time < 0) {
            return;
        }
        super.setShrinkTransition();
        halfHeight = getValueForTime(time, finalTime, oldHalfHeight, newHalfHeight);
        halfWidth = getValueForTime(time, finalTime, oldHalfWidth, newHalfWidth);
    }

    @Override
    boolean isInside(Vector vector) {
        return Math.abs(vector.x()) < halfWidth && Math.abs(vector.y()) < halfHeight;
    }

    @Override
    public GeoPoint randomLocation() {
        Random random = new Random();

        double pos1 = random.nextDouble();
        double pos2 = random.nextDouble();

        int sign1 = 1;
        int sign2 = 1;

        if (pos1 < 0.5) {
            sign1 *= -1;
        }

        if (pos2 < 0.5) {
            sign2 *= -1;
        }

        Vector newCenterVec = new Vector(random.nextDouble() * sign1 * halfWidth, random.nextDouble() * sign2 * halfHeight);

        return center.asOriginTo(newCenterVec);
    }

    @Override
    public void finishShrink() {
        super.finishShrink();
        halfHeight = newHalfHeight;
        halfWidth = newHalfWidth;
    }

    @Override
    public void updateGameArea(Area area) {
        center = area.getLocation();
        halfHeight = ((RectangleArea) area).getHeight() / 2;
        halfWidth = ((RectangleArea) area).getWidth() / 2;
    }

    @Override
    public void displayOn(MapApi mapApi) {
        //TODO implement
    }

    /**
     * Method to get the height of the new area
     *
     * @return the radius
     */
    public double getNewHeight() {
        return newHalfHeight * 2;
    }

    /**
     * Method to get the width of the new area
     *
     * @return the radius
     */
    public double getNewWidth() {
        return newHalfWidth * 2;
    }

    /**
     * Method to get the height of the old area
     *
     * @return the radius
     */
    public double getOldHeight() {
        return oldHalfHeight * 2;
    }

    /**
     * Method to get the width of the old area
     *
     * @return the radius
     */
    public double getOldWidth() {
        return oldHalfWidth * 2;
    }

    @NonNull
    @Override
    public String toString() {
        return "RectangleArea " + getHeight() + " " + getWidth() + " " + center.getLongitude() + " " + center.getLatitude();
    }
}