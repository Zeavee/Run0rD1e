package ch.epfl.sdp.artificial_intelligence;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.EntityConverter;
import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.utils.DependencyFactory;

/**
 * Manages all enemies of a game.
 */
public class EnemyManager implements Updatable {
    public final int UPDATE_EVERY_MS = 1000;

    private ServerDatabaseAPI serverDatabaseAPI;
    private List<Enemy> enemies = new ArrayList<>();
    private long lastUpdateTimeMillis = System.currentTimeMillis();

    public EnemyManager() {
        this.serverDatabaseAPI = DependencyFactory.getServerDatabaseAPI();
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public void update() {
        long currentTimeMillis = System.currentTimeMillis();
        if(lastUpdateTimeMillis - currentTimeMillis >= UPDATE_EVERY_MS) {
            serverDatabaseAPI.sendEnemies(EntityConverter.EnemyToEnemyForFirebase(enemies), value -> {
                if(value.isSuccessful()) { lastUpdateTimeMillis = currentTimeMillis; }
            });
        }
    }
}
