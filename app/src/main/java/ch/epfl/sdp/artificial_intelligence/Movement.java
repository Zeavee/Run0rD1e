package ch.epfl.sdp.artificial_intelligence;

public class Movement {
    private float velocity;
    private float acceleration;
    private double orientation;
    private MovementType movementType;
    private double sinusAmplitude;
    private double sinusAngle;

    public Movement(MovementType movementType) {
        acceleration = 0;
        velocity = 0;
        orientation = 0;
        this.movementType = movementType;
        sinusAngle = 0;
        sinusAmplitude = 1;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType){
        this.movementType = movementType;
    }

    public double getSinusAmplitude() {
        return sinusAmplitude;
    }

    public void setSinusAmplitude(double sinusAmplitude) {
        this.sinusAmplitude = sinusAmplitude;
    }

    public double getSinusAngle() {
        return sinusAngle;
    }

    public void setSinusAngle(double sinusAngle) {
        this.sinusAngle = sinusAngle;
    }
}
