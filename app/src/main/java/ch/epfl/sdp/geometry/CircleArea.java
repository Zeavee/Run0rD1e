package ch.epfl.sdp.geometry;

import android.graphics.Color;

import androidx.annotation.NonNull;

import java.util.Random;

import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.utils.RandomGenerator;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

/**
 * The area where the players can go without losing life
 */
public class CircleArea extends Area {
    private double radius;
    private double oldRadius;
    private double newRadius;

    /**
     * A constructor for the GameArea
     *
     * @param radius the radius of the GameArea
     * @param center the center of the GameArea
     */
    public CircleArea(double radius, GeoPoint center) {
        super(center);
        this.radius = radius;
    }

    @Override
    public void shrink(double factor) {
        if (factor < 0 || factor > 1) {
            return;
        }
        Random random = new Random();

        //random doubles between 0 and 1
        double u = random.nextDouble();
        double v = random.nextDouble();

        //radius/111300.0 is used because we want to convert the radius into degrees
        //we want a point that is not too far from the old center so the new circle can fit in the old one
        double randomDistance = ((1.0 - factor) * radius / 111300.0) * sqrt(u);
        double randomAngle = 2 * Math.PI * v;


        double x = randomDistance * cos(randomAngle);
        double y = randomDistance * sin(randomAngle);

        //this is because of East-West shrinking distances
        double xPrime = x / cos(toRadians(y));

        oldCenter = center;
        oldRadius = radius;

        isShrinking = true;

        newRadius = factor * radius;
        newCenter = new GeoPoint(center.getLongitude() + xPrime, center.getLatitude() + y);
    }

    /**
     * Method to get the radius of the area
     *
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }


    /**
     * Method to get the radius of the new area
     *
     * @return the radius
     */
    public double getNewRadius() {
        return newRadius;
    }

    /**
     * Method to get the radius of the old area
     *
     * @return the radius
     */
    public double getOldRadius() {
        return oldRadius;
    }

    @Override
    public void setShrinkTransition() {
        if (time > finalTime || time < 0) {
            return;
        }
        super.setShrinkTransition();
        radius = getValueForTime(time, finalTime, oldRadius, newRadius);
    }

    @Override
    boolean isInside(Vector vector) {
        return vector.norm() < radius;
    }

    @Override
    public GeoPoint getLocation() {
        return center;
    }

    @Override
    public GeoPoint randomLocation() {
        return new RandomGenerator().randomLocationOnCircle(center, (int) radius);
    }

    @Override
    public void finishShrink() {
        super.finishShrink();
        radius = newRadius;
    }

    @Override
    public void updateGameArea(Area area) {
        center = area.getLocation();
        radius = ((CircleArea) area).getRadius();
    }

    @NonNull
    @Override
    public String toString() {
        return "CircleArea " + radius + " " + center.getLongitude() + " " + center.getLatitude();
    }

    @Override
    public void displayOn(MapApi mapApi) {
        if (isShrinking) {
            setShrinkTransition();
        }
        mapApi.displayCircle(this, Color.RED, (int) radius);
    }
}
