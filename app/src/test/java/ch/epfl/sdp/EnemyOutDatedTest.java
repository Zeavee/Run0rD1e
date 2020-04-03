package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.entity.EnemyOutDated;
import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.map.Displayable;

import static org.junit.Assert.assertEquals;

public class EnemyOutDatedTest {
    private static EnemyOutDated A = new EnemyOutDated(5, 5, 10);

/*    @Test
    public void distanceToTesting() {
        A.updateAoeRadius();
        A.updateLocation();
    }*/

    @Test
    public void getEntityTypeReturnsUser() {
        Displayable currentPlayer = new EnemyOutDated(0,0,0);
        assertEquals(EntityType.ENEMY, currentPlayer.getEntityType());
        assertEquals(true, currentPlayer.isActive());
    }
}
