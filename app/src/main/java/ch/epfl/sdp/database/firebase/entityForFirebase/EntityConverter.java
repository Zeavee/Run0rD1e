package ch.epfl.sdp.database.firebase.entityForFirebase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.ItemBox;

/**
 * A converter used to convert between Firebase stored entity and in-game entity
 */
public class EntityConverter {
    /**
     * Convert from Firebase stored long-term User to the local in-game Player
     *
     * @param userForFirebase The Firebase stored long-term User
     * @return The local in-game Player
     */
    public static Player userForFirebaseToPlayer(UserForFirebase userForFirebase) {
        String username = userForFirebase.getUsername();
        String email = userForFirebase.getEmail();
        int generalScore = userForFirebase.getGeneralScore();

        Player player = new Player(username, email);
        player.setGeneralScore(generalScore);

        return player;
    }

    /**
     * Convert from local in-game Player to Firebase stored in-game Player
     *
     * @param player The local in-game Player
     * @return The firebase stored in-game Player
     */
    public static PlayerForFirebase playerToPlayerForFirebase(Player player) {
        PlayerForFirebase playerForFirebase = new PlayerForFirebase();

        playerForFirebase.setUsername(player.getUsername());
        playerForFirebase.setEmail(player.getEmail());
        playerForFirebase.setGeoPointForFirebase(EntityConverter.geoPointToGeoPointForFirebase(player.getLocation()));
        playerForFirebase.setAoeRadius(player.getAoeRadius());
        playerForFirebase.setHealthPoints(player.getHealthPoints());
        playerForFirebase.setCurrentGameScore(player.getCurrentGameScore());

        return playerForFirebase;
    }

    /**
     * Convert from local in-game GeoPoint to GeoPoint for firebase
     *
     * @param geoPoint The local in-game GeoPoint
     * @return The GeoPoint for firebase
     */
    public static GeoPointForFirebase geoPointToGeoPointForFirebase(GeoPoint geoPoint) {
        return new GeoPointForFirebase(geoPoint.getLongitude(), geoPoint.getLatitude());
    }

    /**
     * Convert from GeoPoint for firebase to local in-game GeoPoint
     *
     * @param geoPointForFirebase The GeoPoint for firebase
     * @return The local in-game GeoPoint
     */
    public static GeoPoint geoPointForFirebaseToGeoPoint(GeoPointForFirebase geoPointForFirebase) {
        return new GeoPoint(geoPointForFirebase.getLongitude(), geoPointForFirebase.getLatitude());
    }

    /**
     * Convert from local in-game Enemies to Enemies stored in cloud firebase
     *
     * @param enemies The list of local in-game Enemy
     * @return The list of enemy stored in cloud Firebase
     */
    public static List<EnemyForFirebase> convertEnemyList(List<Enemy> enemies) {
        List<EnemyForFirebase> enemyForFirebases = new ArrayList<>();
        for (Enemy enemy : enemies) {
            enemyForFirebases.add(new EnemyForFirebase(enemy.getId(), enemy.getBehaviour(), EntityConverter.geoPointToGeoPointForFirebase(enemy.getLocation()), enemy.getMovement().getOrientation()));
        }

        return enemyForFirebases;
    }

    /**
     * Convert from a list of enemies stored in cloud firebase to a list of local in-game enemies
     *
     * @param enemyForFirebaseList The list of enemy stored in cloud firebase
     * @return The list of local in-game enemy
     */
    public static List<Enemy> convertEnemyForFirebaseList(List<EnemyForFirebase> enemyForFirebaseList) {
        List<Enemy> enemyList = new ArrayList<>();
        for (EnemyForFirebase enemyForFirebase : enemyForFirebaseList) {
            Enemy enemy = new Enemy();
            enemy.setId(enemyForFirebase.getId());
            enemy.setBehaviour(enemyForFirebase.getBehaviour());
            enemy.setLocation(EntityConverter.geoPointForFirebaseToGeoPoint(enemyForFirebase.getLocation()));
            if (!EnemyManager.getInstance().enemyExists(enemy)) {
                // TODO: To be done in a more consistent and elegant way ---------
                SinusoidalMovement movement = new SinusoidalMovement();
                movement.setVelocity(10);
                movement.setAngleStep(0.1);
                movement.setAmplitude(1);
                movement.setAngle(1);
                enemy.setMovement(movement);
                // ---------------------------------------------------------------
            }
            enemy.getMovement().setOrientation(enemyForFirebase.getOrientation());
            enemyList.add(enemy);
        }
        return enemyList;
    }

    /**
     * Convert from a list of local in-game players to a list of players stored in cloud firebase
     *
     * @param players The list of local in-game players
     * @return The list of players stored in cloud firebase
     */
    public static List<PlayerForFirebase> convertPlayerList(List<Player> players) {
        ArrayList<PlayerForFirebase> playerList = new ArrayList<>();
        for (Player player : players) {
            PlayerForFirebase playerForFirebase = playerToPlayerForFirebase(player);
            playerList.add(playerForFirebase);
        }

        return playerList;
    }

    /**
     * Convert from player stored in cloud firebase to the local in-game player
     *
     * @param playerForFirebase The player stored in cloud firebase
     * @return The local in-game player
     */
    public static Player playerForFirebaseToPlayer(PlayerForFirebase playerForFirebase) {
        double longitude = playerForFirebase.getGeoPointForFirebase().getLongitude();
        double latitude = playerForFirebase.getGeoPointForFirebase().getLatitude();
        double aoeRadius = playerForFirebase.getAoeRadius();
        String username = playerForFirebase.getUsername();
        String email = playerForFirebase.getEmail();

        return new Player(longitude, latitude, aoeRadius, username, email);
    }

    /**
     * Convert from a map of items to an itemsForFirebase instance
     *
     * @param items The map from the name of the item to the quantity of that item
     * @return The itemsForFirebase instance
     */
    public static ItemsForFirebase convertItems(Map<String, Integer> items) {
        ItemsForFirebase itemsForFirebase = new ItemsForFirebase();
        itemsForFirebase.setItemsMap(items);
        itemsForFirebase.setDate(new Date(System.currentTimeMillis()));

        return itemsForFirebase;
    }

    /**
     * Convert from a map of itemBoxes to a list of itemBoxForFirebase
     *
     * @param itemBoxMap The map from the id of the itemBox to that itemBox instance
     * @return The list of itemBoxForFirebase
     */
    public static List<ItemBoxForFirebase> convertItemBoxMap(Map<String, ItemBox> itemBoxMap) {
        List<ItemBoxForFirebase> itemBoxForFirebaseList = new ArrayList<>();
        for (Map.Entry<String, ItemBox> entry : itemBoxMap.entrySet()) {
            itemBoxForFirebaseList.add(new ItemBoxForFirebase(entry.getKey(), EntityConverter.geoPointToGeoPointForFirebase(entry.getValue().getLocation()), entry.getValue().isTaken()));
        }
        return itemBoxForFirebaseList;
    }
}
