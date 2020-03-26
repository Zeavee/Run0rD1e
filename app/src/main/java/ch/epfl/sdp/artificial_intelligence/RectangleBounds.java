package ch.epfl.sdp.artificial_intelligence;

import ch.epfl.sdp.map.GeoPoint;

public class RectangleBounds implements Boundable {
    private float halfHeight;
    private float halfWidth;
    private GeoPoint lowerLeft;

    public RectangleBounds(float height, float width, GeoPoint lowerLeft){
        this.halfHeight = height / 2;
        this.halfWidth = width / 2;
        this.lowerLeft = lowerLeft;
    }

    public float getHeight()
    {
        return 2 * halfHeight;
    }
    public float getWidth()
    {
        return 2 * halfWidth;
    }
    public GeoPoint getLowerLeftAnchor()
    {
        return lowerLeft;
    }

    @Override
    public boolean isInside(GenPoint genPoint) {
        if (genPoint == null) {
            return false;
        }
        CartesianPoint cp = genPoint.toCartesian();
        return cp.arg1 < halfWidth && cp.arg1 > -halfWidth && cp.arg2 < halfHeight && cp.arg2 > -halfHeight;
    }
}
