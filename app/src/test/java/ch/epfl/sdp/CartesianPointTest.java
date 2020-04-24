package ch.epfl.sdp;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import ch.epfl.sdp.artificial_intelligence.CartesianPoint;

public class CartesianPointTest {
    @Test
    public void getAndSetTest() {
        CartesianPoint cartesianPoint = new CartesianPoint(22, 22);
        cartesianPoint.setArg1(22);
        cartesianPoint.setArg2(22);

        assertEquals(22,cartesianPoint.getArg1(), 0.1);
        assertEquals(22,cartesianPoint.getArg2(), 0.1);
    }
}
