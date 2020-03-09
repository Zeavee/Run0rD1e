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

    public void shrink(double factor) {
        if (factor < 0 || factor > 1) {
            return;
        }
        Random random = new Random();
        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = ((1.0-factor)*radius/111300.0) * sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * cos(t);
        double y = w * sin(t);
        double xPrime = x / cos(toRadians(y));

        radius = factor*radius;
        center = new GeoPoint(center.longitude()+xPrime, center.latitude()+y);
    }

    public GeoPoint getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }
}
