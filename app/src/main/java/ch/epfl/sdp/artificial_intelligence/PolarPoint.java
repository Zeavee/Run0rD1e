package ch.epfl.sdp.artificial_intelligence;

public class PolarPoint extends GenPoint{
    public PolarPoint(double r, double theta) {
        super(r, theta);
    }

    @Override
    public CartesianPoint toCartesian() {
        return new CartesianPoint(arg1 * Math.cos(arg2), arg1 * Math.sin(arg2));
    }

    @Override
    public PolarPoint toPolar() {
        return this;
    }
}
