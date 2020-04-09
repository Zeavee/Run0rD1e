package ch.epfl.sdp.artificial_intelligence;

import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

public class LocalBounds implements Boundable, Localizable {
    private Boundable bounds;
    private CartesianPoint position;

    public LocalBounds(Boundable bounds, GenPoint position) {
        this.bounds = bounds;
        if (position != null) {
            this.position = position.toCartesian();
        }
    }

    public void setBounds(Boundable bounds) {
        this.bounds = bounds;
    }

    @Override
    public boolean isInside(GenPoint genPoint) {
        CartesianPoint cp = genPoint.toCartesian();
        CartesianPoint vect = position.subtract(cp);

        return bounds.isInside(vect);
    }

    public GeoPoint getLocation() {
        return PointConverter.GenPointToGeoPoint(position, MapsActivity.mapApi.getCurrentLocation());
    }

    public void setPosition(GenPoint position) {
        this.position = position.toCartesian();
    }

    @Override
    public GenPoint getPosition() {
        return position;
    }
}
