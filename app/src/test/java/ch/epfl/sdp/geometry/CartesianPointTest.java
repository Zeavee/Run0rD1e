package ch.epfl.sdp.geometry;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import ch.epfl.sdp.geometry.CartesianPoint;

public class CartesianPointTest {
    @Test
    public void getAndSetTest() {
        CartesianPoint cartesianPoint = new CartesianPoint(22, 22);
        assertEquals(22,cartesianPoint.getX(), 0.1);
        assertEquals(22,cartesianPoint.getY(), 0.1);
    }
}
