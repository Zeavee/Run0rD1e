package ch.epfl.sdp;

import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

public class GameArea {
    private double radius;
    private GeoPoint center;

    public GameArea(double radius, GeoPoint center) {
        this.radius = radius;
        this.center = center;
    }

    public GameArea shrink(double factor) {
        if (factor < 0 || factor > 1) {
            return null;
        }
        Random random = new Random();
        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = ((1.0-factor)*radius/111300.0) * sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * cos(t);
        double y = w * sin(t);
        double xPrime = x / cos(toRadians(y));

        double newRadius = factor*radius;
        GeoPoint newCenter = new GeoPoint(center.longitude()+xPrime, center.latitude()+y);

        return new GameArea(newRadius, newCenter);
    }

    public GeoPoint getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public GameArea getShrinkTransition(double time, double finalTime, GameArea startCircle) {
        if (time > finalTime || time < 0 || startCircle == null) {
            return null;
        }
        double outputRadius = getValueForTime(time, finalTime, startCircle.getRadius(), this.radius);
        double outputLatitude = getValueForTime(time, finalTime, startCircle.getCenter().latitude(), this.center.latitude());
        double outputLongitude = getValueForTime(time, finalTime, startCircle.getCenter().longitude(), this.center.longitude());
        return new GameArea(outputRadius, new GeoPoint(outputLongitude, outputLatitude));
    }

    private double getValueForTime(double time, double finalTime, double startValue, double finalValue) {
        return (finalTime-time)/finalTime*startValue + time/finalTime*finalValue;
    }
}
