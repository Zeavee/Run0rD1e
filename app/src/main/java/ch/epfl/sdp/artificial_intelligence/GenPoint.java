package ch.epfl.sdp.artificial_intelligence;

abstract class GenPoint {
    float arg1;
    float arg2;

    GenPoint(float arg1, float arg2){
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    abstract CartesianPoint toCartesian();
    abstract PolarPoint toPolar();
}
