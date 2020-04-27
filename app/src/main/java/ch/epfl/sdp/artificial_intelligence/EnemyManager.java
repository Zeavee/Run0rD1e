package ch.epfl.sdp.artificial_intelligence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.utils.EntityConverter;
import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.utils.DependencyFactory;

/**
 * Manages all enemies of a game.
 */
public class EnemyManager implements Updatable {
    public final int UPDATE_EVERY_MS = 1000;

    private ServerDatabaseAPI serverDatabaseAPI = DependencyFactory.getServerDatabaseAPI();;
    private Map<Integer, Enemy> enemies = new HashMap<>();
    private long lastUpdateTimeMillis = System.currentTimeMillis();

    public void addEnemy(Enemy enemy) {
        enemies.put(enemy.getId(), enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy.getId());
    }

    public List<Enemy> getEnemies() {
        return new ArrayList<>(enemies.values());
    }

    public void updateEnemies(int id, GeoPoint location) {
        if(enemies.containsKey(id)) {
            Enemy enemy = enemies.get(id);
            enemy.setLocation(location);
            enemies.put(id, enemy);
        }
    }

    @Override
    public void update() {
        long currentTimeMillis = System.currentTimeMillis();
        if(lastUpdateTimeMillis - currentTimeMillis >= UPDATE_EVERY_MS) {
            serverDatabaseAPI.sendEnemies(EntityConverter.enemyToEnemyForFirebase(this.getEnemies()), value -> {
                if(value.isSuccessful()) { lastUpdateTimeMillis = currentTimeMillis; }
            });
        }
    }
}
