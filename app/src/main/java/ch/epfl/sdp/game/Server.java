package ch.epfl.sdp.game;

import java.util.List;

import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.artificial_intelligence.EnemyGenerator;
import ch.epfl.sdp.artificial_intelligence.EnemyManager;
import ch.epfl.sdp.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.LocalArea;
import ch.epfl.sdp.geometry.PointConverter;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.ItemBox;

/**
 * Takes care of all actions that a server should perform (generating enemies, updating enemies etc.).
 */
public class Server implements Updatable {
    public final int GENERATE_ENEMY_EVERY_MS = 10000;

    private long lastEnemyGenerateTimeMillis = System.currentTimeMillis();

    private EnemyManager manager;
    private EnemyGenerator enemyGenerator;
    private double damage;
    private List<ItemBox> itemBoxes;

    public Server(EnemyManager manager, EnemyGenerator enemyGenerator) {
        this.manager = manager;
        this.enemyGenerator = enemyGenerator;
    }

    public static void initEnvironment() {
        Game.getInstance().initGame();

        // Enemy -------------------------------------------
        GeoPoint local = new GeoPoint(6.2419, 46.2201);
        GeoPoint enemyPos = new GeoPoint(6.3419, 46.2301);
        LocalArea localArea = new LocalArea(new RectangleArea(3500, 3500), PointConverter.geoPointToCartesianPoint(local));
        Enemy enemy = new Enemy(localArea, new UnboundedArea());
        enemy.setLocation(enemyPos);
        SinusoidalMovement movement = new SinusoidalMovement(PointConverter.geoPointToCartesianPoint(enemyPos));
        movement.setVelocity(5);
        movement.setAngleStep(0.1);
        movement.setAmplitude(10);
        enemy.setMovement(movement);
        Game.getInstance().addToDisplayList(enemy);
        Game.getInstance().addToUpdateList(enemy);
        //  -------------------------------------------

        // ItemBox -------------------------------------------
        Healthpack healthpack = new Healthpack(10);
        ItemBox itemBox = new ItemBox();
        itemBox.putItems(healthpack, 1);
        itemBox.setLocation(new GeoPoint(6.14, 46.22));
        Game.getInstance().addToDisplayList(itemBox);
        Game.getInstance().addToUpdateList(itemBox);
        //  -------------------------------------------
    }

    @Override
    public void update() {
        long currentTimeMillis = System.currentTimeMillis();
        if(currentTimeMillis - lastEnemyGenerateTimeMillis >= GENERATE_ENEMY_EVERY_MS) {
            //Enemy enemy = enemyGenerator.generateEnemy(100);
            Enemy enemy = new Enemy();
            lastEnemyGenerateTimeMillis = currentTimeMillis;
            manager.addEnemy(enemy);
        }

        manager.update();
    }


}
