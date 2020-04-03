package ch.epfl.sdp.artificial_intelligence;

public class CartesianPoint extends GenPoint{
    public CartesianPoint() {
        super();
    }

    public CartesianPoint(double x, double y) {
        super(x, y);
    }

    public CartesianPoint add(GenPoint that){
        return new CartesianPoint(this.arg1 + that.toCartesian().arg1, this.arg2 + that.toCartesian().arg2);
    }

    public void Normalize() {
        double length = Math.sqrt(arg1 * arg1 + arg2 * arg2);
        arg1 /= length;
        arg2 /= length;
    }

    /**
     * Gives the distance from a point relative to this point.
     *
     * @param gp The point we want to know the distance from
     * @return the distance from that point
     */
    public double distanceFrom(GenPoint gp) {
        CartesianPoint cp = gp.toCartesian();
        return Math.sqrt((cp.arg1 - this.arg1) * (cp.arg1 - this.arg1) + (cp.arg2 - this.arg2) * (cp.arg2 - this.arg2));
    }

    public CartesianPoint vector(GenPoint to) {
        CartesianPoint cto = to.toCartesian();
        return new CartesianPoint(cto.arg1 - this.arg1, cto.arg2 - this.arg2);
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
