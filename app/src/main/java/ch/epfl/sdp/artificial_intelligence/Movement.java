package ch.epfl.sdp.artificial_intelligence;

public abstract class Movement {
    private GenPoint position;
    private float velocity;
    private float acceleration;
    private double orientation;

    public Movement(GenPoint initialPosition) {
        position = initialPosition;
        velocity = 1;
        acceleration = 0;
        orientation = 0;
    }

    public GenPoint getPosition() {
        return position;
    }

    public void setPosition(GenPoint position) {
        this.position = position;
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

    public abstract GenPoint nextPosition();
}
