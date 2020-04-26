package ch.epfl.sdp.database.firebase.entity;

import com.google.android.material.badge.BadgeDrawable;

import org.junit.Test;

import ch.epfl.sdp.geometry.GeoPoint;

import static org.junit.Assert.assertEquals;

public class EnemyForFirebaseTest {
    @Test
    public void enemyForFirebaseTest() {
        EnemyForFirebase enemyForFirebase1 = new EnemyForFirebase();
        EnemyForFirebase enemyForFirebase2 = new EnemyForFirebase(0, new GeoPoint(22,22));

        enemyForFirebase1.setId(1);
        enemyForFirebase1.setLocation(new GeoPoint(33,33));
        assertEquals(1, enemyForFirebase1.getId());
        assertEquals(33, enemyForFirebase1.getLocation().getLatitude(), 0.01);
        assertEquals(33, enemyForFirebase1.getLocation().getLongitude(), 0.01);
    }
}
