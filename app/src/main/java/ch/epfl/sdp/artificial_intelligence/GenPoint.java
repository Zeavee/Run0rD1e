package ch.epfl.sdp.artificial_intelligence;

public abstract class GenPoint {
    float arg1;
    float arg2;

    public GenPoint() {
    }

    public GenPoint(float arg1, float arg2){
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public float getArg1() {
        return arg1;
    }
    public float getArg2() {
        return arg2;
    }
    public void setArg1(float arg1) {
        this.arg1 = arg1;
    }

    public void setArg2(float arg2) {
        this.arg2 = arg2;
    }

    public abstract CartesianPoint toCartesian();
    public abstract PolarPoint toPolar();


}
