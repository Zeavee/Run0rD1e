package ch.epfl.sdp.geometry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class VectorTest {
    private Vector vectorA;
    private Vector vectorA2;
    private Vector vectorB;
    private Vector vectorC;
    private Vector vectorD;
    private Vector vectorE;
    private Vector vectorF;
    private Vector vectorG;

    @Before
    public void setup() {
        vectorA = new Vector(1, 2);
        vectorA2 = new Vector(1, 2);
        vectorB = new Vector(-2, 1);
        vectorC = new Vector(-1, 3);
        vectorD = new Vector(3, 1);
        vectorE = new Vector(4, 3);
        vectorF = new Vector(1, 0);
        vectorG = new Vector(-1, 0);
    }

    @Test
    public void equalsIsCorrect() {
        assertEquals(vectorA, vectorA);
        assertEquals(vectorA, vectorA2);
        assertNotEquals(vectorA, vectorB);
    }

    @Test
    public void operationsAreCorrect() {
        assertEquals(vectorB, vectorA.perpendicular());
        assertEquals(vectorC, vectorA.add(vectorB));
        assertEquals(vectorD, vectorB.subtract(vectorA));
        assertEquals(vectorE.normalize().magnitude(), 1, 0);
        assertEquals(vectorF, Vector.fromPolar(1, 0));
        assertEquals(vectorG.invertDirection().x(), 1, 0);
    }
}
