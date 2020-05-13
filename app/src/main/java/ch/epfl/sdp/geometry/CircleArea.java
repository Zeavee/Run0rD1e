package ch.epfl.sdp.geometry;

import android.graphics.Color;

import java.util.Random;

import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapApi;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

/**
 * The area where the players can go without losing life
 */
public class CircleArea implements Displayable, Area {
    private double radius;
    private GeoPoint center;

    /**
     * A constructor for the GameArea
     * @param radius the radius of the GameArea
     * @param center the center of the GameArea
     */
    public CircleArea(double radius, GeoPoint center) {
        this.radius = radius;
        this.center = center;
    }

    /**
     * This method find a smaller GameArea that fits entirely inside the current GameArea
     * @param factor it is a number we multiply with the current GameArea's size to get the new size
     * @return A random GameArea inside the current one
     */
    public CircleArea shrink(double factor) {
        if (factor < 0 || factor > 1) {
            return null;
        }
        Random random = new Random();

        //random doubles between 0 and 1
        double u = random.nextDouble();
        double v = random.nextDouble();

        //radius/111300.0 is used because we want to convert the radius into degrees
        //we want a point that is not too far from the old center so the new circle can fit in the old one
        double randomDistance = ((1.0-factor)*radius/111300.0) * sqrt(u);
        double randomAngle = 2 * Math.PI * v;


        double x = randomDistance * cos(randomAngle);
        double y = randomDistance * sin(randomAngle);

        //this is because of East-West shrinking distances
        double xPrime = x / cos(toRadians(y));

        double newRadius = factor*radius;
        GeoPoint newCenter = new GeoPoint(center.getLongitude()+xPrime, center.getLatitude()+y);

        return new CircleArea(newRadius, newCenter);
    }

    /**
     * Method to get the center of the GameArea
     * @return a GeoPoint which is a location
     */
    public GeoPoint getCenter() {
        return center;
    }

    /**
     * Method to get the radius of the GameArea
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Method that gives all the transitions states to display the shrinking of the GameArea
     * @param time the time which has passed since the start of the shrinking
     * @param finalTime the time when the shrinking will end
     * @param startCircle the old GameArea
     * @return a GameArea we can display for animation the transition
     */
    public CircleArea getShrinkTransition(double time, double finalTime, CircleArea startCircle) {
        if (time > finalTime || time < 0 || startCircle == null) {
            return null;
        }
        double outputRadius = getValueForTime(time, finalTime, startCircle.getRadius(), this.radius);
        double outputLatitude = getValueForTime(time, finalTime, startCircle.getCenter().getLatitude(), this.center.getLatitude());
        double outputLongitude = getValueForTime(time, finalTime, startCircle.getCenter().getLongitude(), this.center.getLongitude());
        return new CircleArea(outputRadius, new GeoPoint(outputLongitude, outputLatitude));
    }

    private double getValueForTime(double time, double finalTime, double startValue, double finalValue) {
        return (finalTime-time)/finalTime*startValue + time/finalTime*finalValue;
    }

    @Override
    public GeoPoint getLocation() {
        return center;
    }

    @Override
    public void displayOn(MapApi mapApi) {
        mapApi.displayCircle(this, Color.RED, (int) radius);
    }

    @Override
    public boolean isInside(CartesianPoint cartesianPoint) {
        return cartesianPoint.distanceFrom(PointConverter.geoPointToCartesianPoint(center)) < radius;
    }
}
