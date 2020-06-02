package ch.epfl.sdp.database.firebase.entityForFirebase;

import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.Behaviour;

import static org.junit.Assert.assertEquals;

public class EnemyForFirebaseTest {
    @Test
    public void enemyForFirebaseTest() {
        EnemyForFirebase enemyForFirebase1 = new EnemyForFirebase();
        EnemyForFirebase enemyForFirebase2 = new EnemyForFirebase(0, Behaviour.WAIT,new GeoPointForFirebase(22,22), 0);

        enemyForFirebase1.setId(1);
        enemyForFirebase1.setLocation(new GeoPointForFirebase(33,33));
        assertEquals(1, enemyForFirebase1.getId());
        assertEquals(33, enemyForFirebase1.getLocation().getLatitude(), 0.01);
        assertEquals(33, enemyForFirebase1.getLocation().getLongitude(), 0.01);
    }
}
