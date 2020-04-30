package ch.epfl.sdp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.utils.EntityConverter;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.GameThread;
import ch.epfl.sdp.game.Updatable;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.utils.DependencyFactory;

/**
 * Manages all enemies of a game.
 */
public class EnemyManager implements Updatable {
    private int counter;
    public final int UPDATE_EVERY_MS = 1000;

    private ServerDatabaseAPI serverDatabaseAPI = DependencyFactory.getServerDatabaseAPI();;
    private Map<Integer, Enemy> enemies = new HashMap<>();
    private long lastUpdateTimeMillis = System.currentTimeMillis();
    private static final EnemyManager instance = new EnemyManager();

    private EnemyManager() {
    }

    public static EnemyManager getInstance() {
        return instance;
    }

    public void addEnemy(Enemy enemy) {
        enemies.put(enemy.getId(), enemy);
    }

    public void addEnemiesToGame(){
        for (Map.Entry<Integer, Enemy> entry : enemies.entrySet()) {
            Game.getInstance().addToDisplayList(entry.getValue());
            Game.getInstance().addToUpdateList(entry.getValue());
        }
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy.getId());
    }

    public List<Enemy> getEnemies() {
        return new ArrayList<>(enemies.values());
    }

    public void updateEnemies(int id, GeoPoint location) {
       /* if(enemies.containsKey(id)) {
            Enemy enemy = enemies.get(id);
            enemy.setLocation(location);
            enemies.put(id, enemy);
        }*/
    }

    @Override
    public void update() {
        if(counter <= 0){
            serverDatabaseAPI.sendEnemies(EntityConverter.enemyToEnemyForFirebase(getEnemies()), value -> {});
            counter = GameThread.FPS + 1;
        }

        --counter;
    }
}
