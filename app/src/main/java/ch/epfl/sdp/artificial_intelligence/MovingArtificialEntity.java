package ch.epfl.sdp.artificial_intelligence;

import java.util.Random;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.MovingEntity;
import ch.epfl.sdp.map.GeoPoint;

public class MovingArtificialEntity extends MovingEntity implements Movable, Localizable, Updatable {
    private GenPoint position;
    private float velocity;
    private float acceleration;
    private double orientation;
    private Movement movement;
    private boolean moving;
    private Boundable bounds;
    public double sinusAmplitude = 1;
    public double sinusAngle;

    public MovingArtificialEntity() {
        super();
        position = new CartesianPoint(0, 0);
        acceleration = 0;
        velocity = 0;
        orientation = 0;
        movement = Movement.LINEAR;
        moving = false;
        bounds = new UnboundedArea();
        forceMove = false;
    }

    public MovingArtificialEntity(Boundable bounds){
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

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
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

    public double sinusAngleStep = 2 * Math.PI / 60;

    private Random rand = new Random();
    private boolean forceMove;
    private CartesianPoint sinusBasePosition;

    public void setForceMove(boolean forceMove) {
        this.forceMove = forceMove;
    }

    public GenPoint move() {
        CartesianPoint cartesianPosition = position.toCartesian();
        CartesianPoint dirVector = new PolarPoint(velocity, orientation).toCartesian();

        switch (movement) {
            case LINEAR:
                return new CartesianPoint(cartesianPosition.arg1 + dirVector.arg1, cartesianPosition.arg2 + dirVector.arg2);
            case SINUSOIDAL:
                return sinusoidalMovement(dirVector);
          /*  case CIRCULAR:
                break;
            case CURVED:
                break;
            case SMOOTH:
                break;
            case RANDOM:
                break;*/
       }

       return null;
   }

    private CartesianPoint sinusoidalMovement(CartesianPoint dirVector) {
        // Perpendicular vec(x,y) = vec(-y,x)
        CartesianPoint pdirVector = new CartesianPoint(-dirVector.arg2, dirVector.arg1);
        pdirVector.Normalize();

        sinusAngle += sinusAngleStep;
        double sine = Math.sin(sinusAngle);

        if (sinusBasePosition == null) {
            sinusBasePosition = position.toCartesian();
        }

        sinusBasePosition = new CartesianPoint(sinusBasePosition.arg1 + dirVector.arg1, sinusBasePosition.arg2 + dirVector.arg2);

        double sinusMoveLength = sine * sinusAmplitude;
        CartesianPoint sinusMove = new CartesianPoint(pdirVector.arg1 * sinusMoveLength, pdirVector.arg2 * sinusMoveLength);
        return new CartesianPoint(sinusBasePosition.arg1 + sinusMove.arg1, sinusBasePosition.arg2 + sinusMove.arg2);
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
            if (bounds.isInside(gp) || forceMove) {
               position = gp;
               //this.setLocation(PointConverter.GenPointToGeoPoint(gp, new GeoPoint(6.149699,46.215788)));
            } else {
                switchOnMouvement();
            }
        }
    }

    private void switchOnMouvement() {
        switch (movement) {
            case LINEAR:
                orientation = rand.nextFloat() * 2 * (float) (Math.PI);
                break;
            case SINUSOIDAL:
                break;
           /* case CIRCULAR:
                break;
            case CURVED:
                break;
            case SMOOTH:
                break;
            case RANDOM:
                break;*/
        }
    }

    @Override
    public EntityType getEntityType() {
        return null;
    }
}
