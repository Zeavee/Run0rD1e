package ch.epfl.sdp.artificial_intelligence;

import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

public class LocalBounds implements Area, Positionable {
    private Area bounds;
    private CartesianPoint position;

    public LocalBounds(Area bounds, GenPoint position) {
        this.bounds = bounds;
        if (position != null) {
            this.position = position.toCartesian();
        }
    }

    public void setBounds(Area bounds) {
        this.bounds = bounds;
    }

    @Override
    public boolean isInside(GenPoint genPoint) {
        CartesianPoint cp = genPoint.toCartesian();
        CartesianPoint vect = position.subtract(cp);

        return bounds.isInside(vect);
    }

    public GeoPoint getLocation() {
        return PointConverter.genPointToGeoPoint(position, MapsActivity.mapApi.getCurrentLocation());
    }

    public void setPosition(GenPoint position) {
        this.position = position.toCartesian();
    }

    @Override
    public GenPoint getPosition() {
        return position;
    }
}
