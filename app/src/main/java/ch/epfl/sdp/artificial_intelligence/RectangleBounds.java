package ch.epfl.sdp.artificial_intelligence;

import ch.epfl.sdp.map.GeoPoint;

public class RectangleBounds implements Boundable {
    private float height;
    private float width;
    private GeoPoint lowerLeft;

    public RectangleBounds(float height, float width, GeoPoint lowerLeft){
        this.height = height;
        this.width = width;
        this.lowerLeft = lowerLeft;
    }

    public float getHeight()
    {
        return height;
    }
    public float getWidth()
    {
        return width;
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
        return cp.arg1 < width && cp.arg1 > -width && cp.arg2 < height && cp.arg2 > -height;
    }
}
