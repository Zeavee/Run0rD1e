package ch.epfl.sdp.artificial_intelligence;

public class PolarPoint extends GenPoint{
    public PolarPoint(float r, float theta) {
        super(r, theta);
    }

    @Override
    public CartesianPoint toCartesian() {
        return new CartesianPoint(Double.valueOf(arg1*Math.cos(arg2)).floatValue(),Double.valueOf(arg1*Math.sin(arg2)).floatValue());
    }

    @Override
    public PolarPoint toPolar() {
        return this;
    }
}
