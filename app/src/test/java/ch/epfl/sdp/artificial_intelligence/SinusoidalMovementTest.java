package ch.epfl.sdp.artificial_intelligence;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SinusoidalMovementTest {
    @Test
    public void testDefaultConstructorCreatesSinusoidalMovement() {
        SinusoidalMovement sinusoidalMovement = new SinusoidalMovement();
        sinusoidalMovement.setAmplitude(10);
        assertEquals(10, sinusoidalMovement.getAmplitude(), 0.0);
        sinusoidalMovement.setAngle(1);
        sinusoidalMovement.setAngleStep(1);
        assertEquals(1, sinusoidalMovement.getAngle(), 0.0);
    }
}
