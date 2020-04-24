package ch.epfl.sdp.geometry;

import org.junit.Test;

import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.map.MockMapApi;
import ch.epfl.sdp.map.MapsActivity;

import static org.junit.Assert.assertEquals;

public class LocalAreaTest {
    @Test
    public void testSetters() {
        Game.getInstance().setMapApi(new MockMapApi());
        GeoPoint geoPoint = new GeoPoint(40, 50);
        CartesianPoint cartesianPoint = PointConverter.geoPointToCartesianPoint(geoPoint);
        LocalArea localArea = new LocalArea(new UnboundedArea(), new CartesianPoint(0, 0));
        localArea.setArea(new UnboundedArea());
        localArea.setPosition(cartesianPoint);
        assertEquals(localArea.getLocation().getLatitude(), geoPoint.getLatitude(), 0.01);
        assertEquals(localArea.getLocation().getLongitude(), geoPoint.getLongitude(), 0.01);
    }
}
