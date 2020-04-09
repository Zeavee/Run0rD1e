package ch.epfl.sdp.artificial_intelligence;

public class SinusoidalMovement extends Movement {
    private double amplitude;
    private double angleStep;
    private double angle;
    private double sine;

    public SinusoidalMovement(GenPoint initialPosition) {
        this(initialPosition, 1, 2 * Math.PI / 60);
    }

    public SinusoidalMovement(GenPoint initialPosition, double amplitude, double angleStep) {
        super(initialPosition);
        this.amplitude = amplitude;
        this.angleStep = angleStep;
        angle = 0;
        sine = 0;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setAngleStep(double angleStep) {
        this.angleStep = angleStep;
    }

    @Override
    public GenPoint nextPosition() {
        CartesianPoint pos = getPosition().toCartesian();
        CartesianPoint dirVector = new PolarPoint(getVelocity(), getOrientation()).toCartesian();
        CartesianPoint pdirVector = new CartesianPoint(-dirVector.arg2, dirVector.arg1); // Perpendicular vec(x,y) = vec(-y,x)

        pdirVector.normalize();
        angle += angleStep;
        sine = Math.sin(angle);

        return pos.add(dirVector).add(pdirVector.multiply(amplitude * sine));
    }
}
