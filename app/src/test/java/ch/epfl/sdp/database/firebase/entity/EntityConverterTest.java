package ch.epfl.sdp.database.firebase.entity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.geometry.GeoPoint;

import static org.junit.Assert.assertEquals;

public class EntityConverterTest {
    @Test
    public void entityConverterTest() {
        UserForFirebase userForFirebase = new UserForFirebase("test@gmail.com", "test", 0);

        Player player = EntityConverter.UserForFirebaseToPlayer(userForFirebase);

        PlayerForFirebase playerForFirebase = EntityConverter.PlayerToPlayerForFirebase(player);

        assertEquals(userForFirebase.getEmail(), playerForFirebase.getEmail());
        assertEquals(userForFirebase.getUsername(), playerForFirebase.getUsername());

        List<Enemy> enemies = new ArrayList<>();
        Enemy enemy = new Enemy();
        enemy.setLocation(new GeoPoint(22,22));
        enemies.add(enemy);

        List<EnemyForFirebase> enemyForFirebases = EntityConverter.EnemyToEnemyForFirebase(enemies);

        assertEquals(enemyForFirebases.get(0).getLocation().getLatitude(), enemies.get(0).getLocation().getLatitude(), 0.01);


    }
}
