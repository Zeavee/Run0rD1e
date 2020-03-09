package ch.epfl.sdp;

import org.junit.Test;

public class EnemyTest {
    private static Enemy A = new Enemy(5, 5, 10);

    @Test
    public void distanceToTesting() {
        A.updateAoeRadius();
        A.updateLocation();
    }
}
