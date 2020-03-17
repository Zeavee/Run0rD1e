package ch.epfl.sdp.artificial_intelligence;

import java.util.Random;

public class MovingEntity implements Movable, Localizable, Updatable{
    private GenPoint position;
    private float velocity;
    private float acceleration;
    private float orientation;
    private Movement movement;
    private boolean moving;
    private Boundable bounds;

    public MovingEntity(){
        position = new CartesianPoint(0,0);
        acceleration = 0;
        velocity = 0;
        orientation = 0;
        movement = Movement.LINEAR;
        moving = false;
        bounds = new UnboundedArea();
    }

    public MovingEntity(Boundable bounds){
        this();
        this.bounds = bounds;
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

    public float getOrientation() {
        return orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement){
        this.movement = movement;
    }

    public Boundable getBounds() {
        return bounds;
    }

    public void setBounds(Boundable bounds) {
        this.bounds = bounds;
    }

    private Random rand = new Random();

    private double sinusAmplitude = 1;
    private double sinusAngle;
    private double sinusAngleStep = 2*Math.PI / 60;
    private CartesianPoint sinusBasePosition;

    public void setSinusAmplitude(double sinusAmplitude) {
        this.sinusAmplitude = sinusAmplitude;
    }

    public void setSinusAngleStep(double sinusAngleStep) {
        this.sinusAngleStep = sinusAngleStep;
    }

    public GenPoint move() {
        CartesianPoint cartesianPosition = position != null ? position.toCartesian() : null;
        CartesianPoint dirVector = new PolarPoint(velocity, orientation).toCartesian();
        switch (movement) {
            case LINEAR:
                return new CartesianPoint(cartesianPosition.arg1 + dirVector.arg1, cartesianPosition.arg2 + dirVector.arg2);
            case SINUSOIDAL:
                // Perpendicular vec(x,y) = vec(-y,x)
                CartesianPoint pdirVector = new CartesianPoint(-dirVector.arg2, dirVector.arg1);
                pdirVector.Normalize();

                sinusAngle += sinusAngleStep;
                double sine = Math.sin(sinusAngle);

                if(sinusBasePosition == null) {
                    sinusBasePosition = position.toCartesian();
                }

                sinusBasePosition = new CartesianPoint(sinusBasePosition.arg1 + dirVector.arg1, sinusBasePosition.arg2 + dirVector.arg2);

                CartesianPoint sinusMove = new CartesianPoint((float)(pdirVector.arg1 * sine * sinusAmplitude),
                        (float)(pdirVector.arg2 * sine * sinusAmplitude));
                return new CartesianPoint(sinusBasePosition.arg1 + sinusMove.arg1, sinusBasePosition.arg2 + sinusMove.arg2);
            case CIRCULAR:
                break;
            case CURVED:
                break;
            case SMOOTH:
                break;
            case RANDOM:
                break;
       }

       return null;
   }

    public void setMoving(boolean moving){
        this.moving = moving;
   }

    @Override
    public boolean isMoving() {
        return moving;
    }

    @Override
    public GenPoint getPosition() {
        return position;
    }

    @Override
    public void update() {
        if(moving) {
            GenPoint gp = move();
            if (bounds.isInside(gp)){
               position = gp;
            } else {
                switchOnMouvement();
            }
        }
    }

    private void switchOnMouvement() {
        switch (movement) {
            case LINEAR:
                orientation = rand.nextFloat()*2*(float)(Math.PI);
                break;
            case SINUSOIDAL:
                break;
            case CIRCULAR:
                break;
            case CURVED:
                break;
            case SMOOTH:
                break;
            case RANDOM:
                break;
        }
    }
}
