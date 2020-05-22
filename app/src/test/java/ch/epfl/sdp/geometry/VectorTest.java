package ch.epfl.sdp.geometry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VectorTest {
    Vector vectorA;
    Vector vectorA2;
    Vector vectorB;
    Vector vectorC;
    Vector vectorD;
    Vector vectorE;
    Vector vectorF;
    Vector vectorG;

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
        assertTrue(vectorA.equals(vectorA));
        assertTrue(vectorA.equals(vectorA2));
        assertFalse(vectorA.equals(vectorB));
    }

    @Test
    public void operationsAreCorrect() {
        assertTrue(vectorB.equals(vectorA.perpendicular()));
        assertTrue(vectorC.equals(vectorA.add(vectorB)));
        assertTrue(vectorD.equals(vectorB.subtract(vectorA)));
        assertEquals(vectorE.normalize().magnitude(), 1, 0);
        assertTrue(vectorF.equals(Vector.fromPolar(1, 0)));
        assertEquals(vectorG.invertDirection().x(), 1, 0);
    }
}
