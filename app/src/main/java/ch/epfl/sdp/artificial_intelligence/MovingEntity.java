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

    public GenPoint move(){
       switch (movement){
           case LINEAR:
               CartesianPoint cp = new PolarPoint(velocity, orientation).toCartesian();
               CartesianPoint cp2 = position.toCartesian();
               return new CartesianPoint(cp.arg1 + cp2.arg1, cp.arg2 + cp2.arg2);
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
            if(bounds.isInside(gp)){
               position = gp;
            }else {
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
    }
}
