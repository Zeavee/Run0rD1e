package ch.epfl.sdp.artificial_intelligence;

public class LinearMovement extends Movement {
    public LinearMovement(GenPoint initialPosition) {
        super(initialPosition);
    }

    @Override
    public GenPoint nextPosition() {
        CartesianPoint cartesianPosition = getPosition().toCartesian();
        CartesianPoint dirVector = new PolarPoint(getVelocity(), getOrientation()).toCartesian();

        return new CartesianPoint(cartesianPosition.arg1 + dirVector.arg1, cartesianPosition.arg2 + dirVector.arg2);
    }
}
