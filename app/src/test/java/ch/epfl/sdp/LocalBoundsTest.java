package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.CartesianPoint;
import ch.epfl.sdp.artificial_intelligence.GenPoint;
import ch.epfl.sdp.artificial_intelligence.LocalBounds;
import ch.epfl.sdp.artificial_intelligence.PointConverter;
import ch.epfl.sdp.artificial_intelligence.UnboundedArea;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

import static org.junit.Assert.assertEquals;

public class LocalBoundsTest {
    @Test
    public void testSetters() {
        MapsActivity.setMapApi(new MockMapApi());
        GeoPoint geoPoint = new GeoPoint(40, 50);
        GenPoint genPoint = PointConverter.GeoPointToGenPoint(geoPoint);
        LocalBounds localBounds = new LocalBounds(new UnboundedArea(), new CartesianPoint());
        localBounds.setBounds(new UnboundedArea());
        localBounds.setPosition(genPoint);
        assertEquals(localBounds.getLocation().getLatitude(), geoPoint.getLatitude(), 0.01);
        assertEquals(localBounds.getLocation().getLongitude(), geoPoint.getLongitude(), 0.01);
    }
}
