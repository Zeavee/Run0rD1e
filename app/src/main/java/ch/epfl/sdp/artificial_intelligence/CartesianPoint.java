package ch.epfl.sdp.artificial_intelligence;

class CartesianPoint extends GenPoint{
    CartesianPoint(float x, float y) {
        super(x, y);
    }

    @Override
    CartesianPoint toCartesian() {
        return this;
    }

    @Override
    PolarPoint toPolar() {
        return new PolarPoint(Double.valueOf(Math.sqrt(arg1*arg1 + arg2*arg2)).floatValue(),Double.valueOf(Math.atan2(arg2,arg1)).floatValue());
    }
}
