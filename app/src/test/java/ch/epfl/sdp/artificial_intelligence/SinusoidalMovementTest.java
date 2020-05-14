package ch.epfl.sdp.artificial_intelligence;

import org.junit.Test;

import ch.epfl.sdp.geometry.GeoPoint;

import static org.junit.Assert.assertTrue;

public class SinusoidalMovementTest {
    @Test
    public void testDefaultConstructor() {
        SinusoidalMovement sinusoidalMovement = new SinusoidalMovement();
        sinusoidalMovement.setAmplitude(10);
        assertTrue(sinusoidalMovement.getAmplitude() == 10);
        sinusoidalMovement.setAngle(1);
        sinusoidalMovement.setAngleStep(1);
        assertTrue(sinusoidalMovement.getAngle() == 1);
    }
}
