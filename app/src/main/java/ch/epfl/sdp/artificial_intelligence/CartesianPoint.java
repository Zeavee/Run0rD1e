package ch.epfl.sdp.artificial_intelligence;

public class CartesianPoint extends GenPoint{
    public CartesianPoint(float x, float y) {
        super(x, y);
    }

    @Override
    public CartesianPoint toCartesian() {
        return this;
    }

    @Override
    public PolarPoint toPolar() {
        return new PolarPoint(Double.valueOf(Math.sqrt(arg1*arg1 + arg2*arg2)).floatValue(),Double.valueOf(Math.atan2(arg2,arg1)).floatValue());
    }
}
