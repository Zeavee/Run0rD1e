package ch.epfl.sdp.artificial_intelligence;

import com.google.firebase.firestore.util.Assert;

import java.util.Random;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.MovingEntity;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

public class MovingArtificialEntity extends MovingEntity implements Movable, Localizable, Updatable {
    private GenPoint position;
    private boolean moving;
    private Boundable bounds;
    private Movement movement;
    private boolean isActive;

    public MovingArtificialEntity() {
        super();
        position = new CartesianPoint(0, 0);
        moving = false;
        bounds = new UnboundedArea();
        forceMove = false;
        isActive = true;
        movement = new Movement();
    }

    public MovingArtificialEntity(Boundable bounds){
        this();
        this.bounds = bounds;
    }

    public void setPosition(GenPoint position) {
        this.position = position;
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
        CartesianPoint dirVector = new PolarPoint(movement.getVelocity(), movement.getOrientation()).toCartesian();

        switch (movement.getMovementType()) {
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

        movement.setSinusAngle(movement.getSinusAngle()+sinusAngleStep);
        double sine = Math.sin(movement.getSinusAngle());

        if (sinusBasePosition == null) {
            sinusBasePosition = position.toCartesian();
        }

        sinusBasePosition = new CartesianPoint(sinusBasePosition.arg1 + dirVector.arg1, sinusBasePosition.arg2 + dirVector.arg2);

        CartesianPoint sinusMove = new CartesianPoint((float) (pdirVector.arg1 * sine * movement.getSinusAmplitude()),
                (float) (pdirVector.arg2 * sine * movement.getSinusAmplitude()));
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
               //this.setLocation(PointConverter.GenPointToGeoPoint(gp, MapsActivity.mapApi.getCurrentLocation()));
               this.setLocation(PointConverter.GenPointToGeoPoint(gp, new GeoPoint(6.149699, 46.215788)));
            } else {
                switchOnMouvement();
            }
        }
    }

    private void switchOnMouvement() {
        switch (movement.getMovementType()) {
            case LINEAR:
                movement.setOrientation(rand.nextFloat() * 2 * (float) (Math.PI));
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

    public Movement getMovement() {
        return movement;
    }

    @Override
    public EntityType getEntityType() {
        return null;
    }

    @Override
    public Boolean isActive() {
        return isActive;
    }
}
