package ch.epfl.sdp.geometry;

import android.graphics.Color;

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

        radius = factor * radius;
        center = new GeoPoint(center.getLongitude() + xPrime, center.getLatitude() + y);
    }

    /**
     * Method to get the radius of the GameArea
     *
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public CircleArea getShrinkTransition() {
        if (time > finalTime || time < 0) {
            return null;
        }
        double outputRadius = getValueForTime(time, finalTime, oldRadius, radius);
        double outputLatitude = getValueForTime(time, finalTime, oldCenter.getLatitude(), center.getLatitude());
        double outputLongitude = getValueForTime(time, finalTime, oldCenter.getLongitude(), center.getLongitude());
        return new CircleArea(outputRadius, new GeoPoint(outputLongitude, outputLatitude));
    }

    private double getValueForTime(double time, double finalTime, double startValue, double finalValue) {
        return (finalTime - time) / finalTime * startValue + time / finalTime * finalValue;
    }

    @Override
    protected boolean isInside(Vector vector) {
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
        isShrinking = false;
    }

    @Override
    public void displayOn(MapApi mapApi) {
        if (isShrinking) {
            CircleArea tempCircleArea = getShrinkTransition();
            mapApi.displayCircle(tempCircleArea, Color.RED, (int) tempCircleArea.radius);
        } else {
            mapApi.displayCircle(this, Color.RED, (int) radius);
        }
    }
}
