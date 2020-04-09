package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.CartesianPoint;
import ch.epfl.sdp.artificial_intelligence.GenPoint;

import static org.junit.Assert.assertTrue;

public class GenPointTest {
    private static GenPoint genPoint;


    @Test
    public void setArgsTest() {
        genPoint = new CartesianPoint();

        genPoint.setArg1(1);
        genPoint.setArg2(2);
        assertTrue(genPoint.getArg1() == 1);
        assertTrue(genPoint.getArg2() == 2);
    }
}
