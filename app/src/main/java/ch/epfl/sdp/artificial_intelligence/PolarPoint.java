package ch.epfl.sdp.artificial_intelligence;

class PolarPoint extends GenPoint{
    PolarPoint(float r, float theta) {
        super(r, theta);
    }

    @Override
    CartesianPoint toCartesian() {
        return new CartesianPoint(Double.valueOf(arg1*Math.cos(arg2)).floatValue(),Double.valueOf(arg1*Math.sin(arg2)).floatValue());
    }

    @Override
    PolarPoint toPolar() {
        return this;
    }
}
