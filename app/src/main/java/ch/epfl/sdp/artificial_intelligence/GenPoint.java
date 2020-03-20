package ch.epfl.sdp.artificial_intelligence;

public abstract class GenPoint {
    double arg1;
    double arg2;

    public GenPoint(double arg1, double arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public double getArg1() {
        return arg1;
    }

    public double getArg2() {
        return arg2;
    }

    public abstract CartesianPoint toCartesian();
    public abstract PolarPoint toPolar();
}
