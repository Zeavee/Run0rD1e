package ch.epfl.sdp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnemyTest {
    private static Enemy A = new Enemy(5, 5, 10);

    @Test
    public void distanceToTesting() {
        A.updateAoeRadius();
        A.updateLocation();
    }

    @Test
    public void getEntityTypeReturnsUser() {
        Displayable currentPlayer = new Enemy(0,0,0);
        assertEquals(EntityType.ENEMY, currentPlayer.getEntityType());
    }
}
