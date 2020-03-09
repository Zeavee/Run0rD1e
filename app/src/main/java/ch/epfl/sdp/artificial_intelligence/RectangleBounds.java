package ch.epfl.sdp.artificial_intelligence;

public class RectangleBounds implements Boundable {
    private float height;
    private float width;

    RectangleBounds(float height, float width){
        this.height = height;
        this.width = width;
    }

    @Override
    public boolean isInside(GenPoint genPoint) {
        CartesianPoint cp = genPoint.toCartesian();
        return cp.arg1 < width && cp.arg1 > -width && cp.arg2 < height && cp.arg2 > -height;
    }
}
