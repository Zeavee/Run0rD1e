package ch.epfl.sdp.game;

import java.util.ArrayList;

import ch.epfl.sdp.artificial_intelligence.RandomEnemyGenerator;
import ch.epfl.sdp.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.CircleArea;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.item.Coin;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.ItemBox;

public class SoloMode implements Updatable {
    private PlayerManager playerManager = PlayerManager.getInstance();
    private Thread initThread;
    private Area gameArea;

    public void start() {
        initThread = new Thread() {
            @Override
            public void run() {
                // wait until the currentUser location being updated for the first time
                while (playerManager.getCurrentUser().getLocation().getLatitude() == 0 && playerManager.getCurrentUser().getLocation().getLongitude() == 0) { }

                // init the environment
                initGameArea();
                initItemBoxes();
                initEnemies();
                initCoins();

                // start the Game thread
                Game.getInstance().initGame();
            }
        };

        initThread.setName("initThread");
        initThread.start();
    }

    @Override
    public void update() {
        checkGameEnd();
    }

    private void initGameArea() {
        //GameArea -----------------------------------------
        GeoPoint local = PlayerManager.getInstance().getCurrentUser().getLocation();
        gameArea = new CircleArea(3000, local);
        Game.getInstance().addToDisplayList(gameArea);
        Game.getInstance().areaShrinker.setGameArea(gameArea);
    }

    private void initEnemies() {
        // Enemy -------------------------------------------
        Area area = new UnboundedArea();
        RandomEnemyGenerator randomEnemyGenerator = new RandomEnemyGenerator(gameArea, area);
        randomEnemyGenerator.setEnemyCreationTime(1000);
        randomEnemyGenerator.setMaxEnemies(10);
        randomEnemyGenerator.setMinDistanceFromEnemies(100);
        randomEnemyGenerator.setMinDistanceFromPlayers(100);
        randomEnemyGenerator.generateEnemy(100);
        Enemy enemy = randomEnemyGenerator.getEnemies().get(0);
        SinusoidalMovement movement = new SinusoidalMovement(10, 0.1);
        enemy.setMovement(movement);
        Game.getInstance().addToDisplayList(enemy);
        Game.getInstance().addToUpdateList(enemy);
        //  -------------------------------------------
    }

    private void initCoins() {
        int amount = 10;
        ArrayList<Coin> coins = Coin.generateCoinsAroundLocation(playerManager.getCurrentUser().getLocation(), amount);
        for (Coin c : coins) {
            Game.getInstance().addToDisplayList(c);
            Game.getInstance().addToUpdateList(c);
        }
    }

    private void initItemBoxes() {
        // ItemBox -------------------------------------------
        Healthpack healthpack = new Healthpack(10);
        ItemBox itemBox = new ItemBox(new GeoPoint(6.14, 46.22));
        itemBox.putItems(healthpack, 2);
        Game.getInstance().addToDisplayList(itemBox);
        Game.getInstance().addToUpdateList(itemBox);

        Healthpack healthpack1 = new Healthpack(10);
        ItemBox itemBox1 = new ItemBox(new GeoPoint(6.1488, 46.2125));
        itemBox1.putItems(healthpack1, 1);
        Game.getInstance().addToDisplayList(itemBox1);
        Game.getInstance().addToUpdateList(itemBox1);
        //  -------------------------------------------
    }

    private void checkGameEnd() {

    }
}
