package ch.epfl.sdp.database.firebase.entityForFirebase;

import org.junit.Test;

import ch.epfl.sdp.entities.artificial_intelligence.Behaviour;

import static org.junit.Assert.assertEquals;

public class EnemyForFirebaseTest {
    @Test
    public void enemyForFirebaseTest() {
        EnemyForFirebase enemyForFirebase1 = new EnemyForFirebase();

        enemyForFirebase1.setId(1);
        enemyForFirebase1.setLocation(new GeoPointForFirebase(33,33));
        assertEquals(1, enemyForFirebase1.getId());
        assertEquals(33, enemyForFirebase1.getLocation().getLatitude(), 0.01);
        assertEquals(33, enemyForFirebase1.getLocation().getLongitude(), 0.01);

        enemyForFirebase1.setBehaviour(Behaviour.WAIT);
        assertEquals(Behaviour.WAIT, enemyForFirebase1.getBehaviour());

        enemyForFirebase1.setOrientation(9.9);
        assertEquals(9.9, enemyForFirebase1.getOrientation(), 0.01);
    }
}
