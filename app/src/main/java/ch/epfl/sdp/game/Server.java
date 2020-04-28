package ch.epfl.sdp.game;

import ch.epfl.sdp.artificial_intelligence.EnemyGenerator;
import ch.epfl.sdp.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.utils.EntityConverter;
import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.EnemyManager;
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
                SinusoidalMovement movement = new SinusoidalMovement(PointConverter.geoPointToCartesianPoint(enemyPos));
                movement.setVelocity(5);
                movement.setAngleStep(0.1);
                movement.setAmplitude(10);
                enemy.setMovement(movement);
                Game.getInstance().addToDisplayList(enemy);
                Game.getInstance().addToUpdateList(enemy);
                EnemyManager.getInstance().addEnemy(enemy);
                //  -------------------------------------------

                serverDatabaseAPI.sendEnemies(EntityConverter.enemyToEnemyForFirebase(EnemyManager.getInstance().getEnemies()), value -> {
                    if (value.isSuccessful()){
                        serverDatabaseAPI.startGame(value1 -> {
                            if(value1.isSuccessful()){
                                //maybe not working
                                Server.super.initEnvironment();
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

    @Override
    public void update() {
        long currentTimeMillis = System.currentTimeMillis();
        if(currentTimeMillis - lastEnemyGenerateTimeMillis >= generateEnemyEveryMs) {
            Enemy enemy = new Enemy();
            lastEnemyGenerateTimeMillis = currentTimeMillis;
            EnemyManager.getInstance().addEnemy(enemy);
        }

        EnemyManager.getInstance().update();
    }
}
