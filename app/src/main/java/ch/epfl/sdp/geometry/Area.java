package ch.epfl.sdp.geometry;

import androidx.annotation.NonNull;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.GameThread;
import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.map.Displayable;

/**
 * Represents an area in the 2D plane, which can move and shrink.
 */
public abstract class Area implements Positionable, Displayable, Updatable {
    GeoPoint center;
    GeoPoint oldCenter;
    GeoPoint newCenter;
    double time;
    double finalTime;
    boolean isShrinking;

    private int damageDelay = GameThread.FPS;
    String remainingTimeString;

    /**
     * A constructor for an area
     *
     * @param center the center of the area
     */
    public Area(GeoPoint center) {
        this.center = center;
    }

    /**
     * This method tells if the area is currently shrinking
     *
     * @return a boolean that tells if the area is currently shrinking
     */
    boolean isShrinking() {
        return isShrinking;
    }

    /**
     * This method tells if the given location is inside the area
     *
     * @param geoPoint the location we want to know if it is inside the area
     * @return a boolean that tells if the given location is inside the area
     */
    public boolean isInside(GeoPoint geoPoint) {
        Vector vec = center.toVector().subtract(geoPoint.toVector());
        return isInside(vec);
    }

    /**
     * This method finds a smaller area that fits entirely inside the current area
     *
     * @param factor it is a number we multiply with the current area's size to get the new size
     */
    public abstract void shrink(double factor);

    /**
     * This method set the field of the area for displaying a transition state depending on the time set before
     */
    public void setShrinkTransition() {
        double outputLatitude = getValueForTime(time, finalTime, oldCenter.getLatitude(), newCenter.getLatitude());
        double outputLongitude = getValueForTime(time, finalTime, oldCenter.getLongitude(), newCenter.getLongitude());
        center = new GeoPoint(outputLongitude, outputLatitude);
    }

    /**
     * This method sets the time passed since the shrinking started
     *
     * @param time the time since the shrinking started
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * This method sets the time when the shrinking will end
     *
     * @param finalTime the time when the shrinking will end
     */
    void setFinalTime(double finalTime) {
        this.finalTime = finalTime;
    }

    /**
     * Check if the the vector (point) is inside the area.
     * @param vector The vector to be checked.
     * @return True if and only if the vector is inside the area.
     */
    abstract boolean isInside(Vector vector);

    @Override
    public GeoPoint getLocation() {
        return center;
    }

    /**
     * This method gives the old location of the center when the area is shrinking
     *
     * @return the old location of the center when the area is shrinking
     */
    GeoPoint getOldLocation() {
        return oldCenter;
    }

    /**
     * This method gives the new location of the center when the area is shrinking
     *
     * @return the new location of the center when the area is shrinking
     */
    public GeoPoint getNewLocation() {
        return newCenter;
    }

    /**
     * This method gives a random location inside the area
     *
     * @return a random location inside the area
     */
    public abstract GeoPoint randomLocation();

    /**
     * This method finishes the shrinking
     */
    public void finishShrink() {
        isShrinking = false;
        center = newCenter;
    }

    /**
     * Get the value which will serve as parameter for an area.
     * For example, the radius of a circle:
     * Begin with the start radius and while the time passes
     * the radius value will approach the final value.
     * @param time The time that varies.
     * @param finalTime The final time (fixed).
     * @param startValue The beginning value (fixed).
     * @param finalValue The value when time equals final time (fixed).
     * @return The value given by a function over time.
     */
    protected double getValueForTime(double time, double finalTime, double startValue, double finalValue) {
        return (finalTime - time) / finalTime * startValue + time / finalTime * finalValue;
    }

    /**
     * Modify the game area according to the area passed as argument.
     * For example, we could change the center or radius of a circle area.
     * @param area The area to get the modifications from.
     */
    public abstract void updateGameArea(Area area);

    @Override
    public void update() {
        if (damageDelay > 0) {
            damageDelay--;
            return;
        }
        damageDelay = GameThread.FPS;
        for (Player player : PlayerManager.getInstance().getPlayers()) {
            if (!isInside(player.getLocation())) {
                int damage = 10;
                player.setHealthPoints(player.getHealthPoints() - damage);
            }
        }
    }

    @NonNull
    @Override
    public abstract String toString();

    /**
     * This method sets the remaining time string we should display on the timer
     *
     * @param remainingTimeString the string representing the remaining time
     */
    public void setRemainingTimeString(String remainingTimeString) {
        this.remainingTimeString = remainingTimeString;
    }

    /**
     * This method gets the remaining time string we should display on the timer
     */
    public String getRemainingTimeString() {
        return remainingTimeString;
    }
}
