package ch.epfl.sdp.artificial_intelligence;

import android.graphics.Point;

import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

public class RectangleBounds implements Boundable {
    private float halfHeight;
    private float halfWidth;
    private double leftBound;
    private double rightBound;
    private double upperBound;
    private double lowerBound;
    private GenPoint midPoint;

    public RectangleBounds(float height, float width, GeoPoint midPoint){
        this.halfHeight = height / 2;
        this.halfWidth = width / 2;
        this.midPoint = PointConverter.GeoPointToGenPoint(midPoint);
        this.leftBound = this.midPoint.toCartesian().arg1 - width / 2;
        this.rightBound = this.midPoint.toCartesian().arg1 + width / 2;
        this.lowerBound = this.midPoint.toCartesian().arg2 - width / 2;
        this.upperBound = this.midPoint.toCartesian().arg2 + width / 2;
    }

    public float getHeight()
    {
        return 2 * halfHeight;
    }
    public float getWidth()
    {
        return 2 * halfWidth;
    }

    public GeoPoint getMidPoint() {
        return PointConverter.GenPointToGeoPoint(this.midPoint, MapsActivity.mapApi.getCurrentLocation());
    }

    @Override
    public boolean isInside(GenPoint genPoint) {
        if (genPoint == null) {
            return false;
        }
        CartesianPoint cp = genPoint.toCartesian();
        return cp.arg1 < rightBound && cp.arg1 > leftBound && cp.arg2 < upperBound && cp.arg2 > lowerBound;
    }
}
