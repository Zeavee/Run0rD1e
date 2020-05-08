package ch.epfl.sdp.geometry;

import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.map.MockMap;

import static org.junit.Assert.assertEquals;

public class LocalAreaTest {
    @Test
    public void testSetters() {
        Game.getInstance().setMapApi(new MockMap());
        GeoPoint geoPoint = new GeoPoint(40, 50);
        PlayerManager.getInstance().setCurrentUser(new Player(40, 50, 10, "owner", "owner@owner.com"));
        CartesianPoint cartesianPoint = PointConverter.geoPointToCartesianPoint(geoPoint);
        LocalArea localArea = new LocalArea(new UnboundedArea(), new CartesianPoint(0, 0));
        localArea.setArea(new UnboundedArea());
        localArea.setPosition(cartesianPoint);
        assertEquals(localArea.getLocation().getLatitude(), geoPoint.getLatitude(), 0.01);
        assertEquals(localArea.getLocation().getLongitude(), geoPoint.getLongitude(), 0.01);
    }
}
