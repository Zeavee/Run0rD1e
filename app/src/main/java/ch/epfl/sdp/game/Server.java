package ch.epfl.sdp.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.artificial_intelligence.EnemyGenerator;
import ch.epfl.sdp.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.EntityConverter;
import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.LocalArea;
import ch.epfl.sdp.geometry.PointConverter;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.utils.DependencyFactory;

/**
 * Takes care of all actions that a server should perform (generating enemies, updating enemies etc.).
 */
public class Server extends Client {
    private int counter;
    public final int GENERATE_ENEMY_EVERY_MS = 10000;
    private long lastEnemyGenerateTimeMillis = System.currentTimeMillis();
    private EnemyGenerator enemyGenerator;
    //private double damage;
    //private List<ItemBox> itemBoxes;
    private int generateEnemyEveryMs;
    ServerDatabaseAPI serverDatabaseAPI;

    public Server(){
        serverDatabaseAPI = DependencyFactory.getServerDatabaseAPI();
    }

    public Server(EnemyManager manager, EnemyGenerator enemyGenerator) {
        this.enemyGenerator = enemyGenerator;
        this.generateEnemyEveryMs = GENERATE_ENEMY_EVERY_MS;
        Game.getInstance().addToUpdateList(this);
    }

    public Server(EnemyManager manager, EnemyGenerator enemyGenerator, int generateEnemyEveryMs) {
        this(manager, enemyGenerator);
        this.generateEnemyEveryMs = generateEnemyEveryMs;
    }

    public void initEnvironment() {
        serverDatabaseAPI.listenToNumOfPlayers(res -> {
            if(res.isSuccessful()) {
                // Enemy -------------------------------------------
                // TODO USE random enemy generator to generate enemy
                GeoPoint local = new GeoPoint(6.2419, 46.2201);
                GeoPoint enemyPos = new GeoPoint(6.3419, 46.2301);
                LocalArea localArea = new LocalArea(new RectangleArea(3500, 3500), PointConverter.geoPointToCartesianPoint(local));
                Enemy enemy = new Enemy(0, localArea, new UnboundedArea());
                enemy.setLocation(enemyPos);
                enemy.setAoeRadius(100);
                SinusoidalMovement movement = new SinusoidalMovement(PointConverter.geoPointToCartesianPoint(enemyPos));
                movement.setVelocity(15);
                movement.setAngleStep(0.1);
                movement.setAmplitude(10);
                enemy.setMovement(movement);
                EnemyManager.getInstance().addEnemy(enemy);
                EnemyManager.getInstance().addEnemiesToGame();
                //  -------------------------------------------

                serverDatabaseAPI.sendEnemies(EntityConverter.enemyToEnemyForFirebase(EnemyManager.getInstance().getEnemies()), value -> {
                    if (value.isSuccessful()){
                        serverDatabaseAPI.startGame(value1 -> {
                            if(value1.isSuccessful()){
                                //maybe not working
                                Server.super.initEnvironment();
                                sendDamage();
                            }
                        });
                    }
                });
            }
        });

        /**
         * Following code is just used to test the functionality of firebase functions
         */
//        DependencyFactory.getServerDatabaseAPI().sendEnemies(EntityConverter.enemyToEnemyForFirebase(manager.getEnemies()), value -> {
//            if(value.isSuccessful()) {
//                Log.d(">>>>>>>>>>TAG>>>>>>>>", "finish: SENDING ENEMIES");
//            }
//        });
//        DependencyFactory.getServerDatabaseAPI().startGame();
    }

    private void sendDamage(){
        serverDatabaseAPI.sendDamage(EntityConverter.convertPlayerList(playerManager.getPlayers()), value -> {});
    }


    @Override
    public void update() {
        super.update();

        if(counter <= 0){
            EnemyManager.getInstance().update();
            sendDamage();
            counter = GameThread.FPS + 1;
        }

        --counter;
    }
}