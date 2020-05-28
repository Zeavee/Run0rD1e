package ch.epfl.sdp.artificial_intelligence;

import android.util.Log;

import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.Vector;

import static java.lang.Math.sin;

/**
 * Represent a sinusoidal evolution of a movement.
 */
public class SinusoidalMovement extends Movement {
    private double amplitude;
    private double angleStep;
    private double angle;

    /**
     * Creates a movement which is sinusoidal.
     *
     */
    public SinusoidalMovement() {
        this(1, 2 * Math.PI / 60);
    }

    /**
     * Creates a movement which is sinusoidal based on it's amplitude and angle step.
     *  @param amplitude       The amplitude of the sine function.
     * @param angleStep       The step value to be added to the angle after each move.
     */
    public SinusoidalMovement(double amplitude, double angleStep) {
        super();
        this.amplitude = amplitude;
        this.angleStep = angleStep;
        angle = 0;
    }

    /**
     * Gets the sine amplitude.
     *
     * @return A value representing the sine amplitude.
     */
    public double getAmplitude() {
        return amplitude;
    }

    /**
     * Sets the sine amplitude.
     *
     * @param amplitude The value representing the sine amplitude.
     */
    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    /**
     * Gets the angle of the sine function.
     *
     * @return A value representing the angle of the sine function.
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Sets the sine angle.
     *
     * @param angle The value representing the sine angle.
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Sets the angle step.
     *
     * @param angleStep The value which is added to the angle after each move.
     */
    public void setAngleStep(double angleStep) {
        this.angleStep = angleStep;
    }

    @Override
    public GeoPoint nextPosition(GeoPoint from) {
        if(getVelocity() == 0){
            return from;
        }

        Vector dirVector = Vector.fromPolar(getVelocity(), getOrientation());
        Vector sineVector = dirVector.perpendicular().normalize().multiplyByScalar(amplitude * sin(angle));

        angle += angleStep;
        return from.asOriginTo(dirVector.add(sineVector));
    }
}
