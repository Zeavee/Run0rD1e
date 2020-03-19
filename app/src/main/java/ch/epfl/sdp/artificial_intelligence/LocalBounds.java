package ch.epfl.sdp.artificial_intelligence;

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
        CartesianPoint vect = position.vector(cp);

        return bounds.isInside(vect);
    }

    @Override
    public GenPoint getPosition() {
        return position;
    }

    public void setPosition(GenPoint position) {
        this.position = position.toCartesian();
    }
}
