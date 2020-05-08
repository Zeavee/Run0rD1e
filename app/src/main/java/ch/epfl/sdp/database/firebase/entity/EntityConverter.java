package ch.epfl.sdp.database.firebase.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.Player;
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
        playerForFirebase.setLongitude(player.getLocation().getLongitude());
        playerForFirebase.setLatitude(player.getLocation().getLatitude());
        playerForFirebase.setAoeRadius(player.getAoeRadius());
        playerForFirebase.setHealthPoints(player.getHealthPoints());
        playerForFirebase.setCurrentGameScore(player.getCurrentGameScore());

        return playerForFirebase;
    }

    /**
     * Convert from local in-game Enemy to Firebase stored in-game Enemy
     *
     * @param enemies The list of local in-game Enemy
     * @return The list of Firebase stored in-game Enemy
     */
    public static List<EnemyForFirebase> convertEnemyList(List<Enemy> enemies) {
        List<EnemyForFirebase> enemyForFirebases = new ArrayList<>();
        for (Enemy enemy : enemies) {
            enemyForFirebases.add(new EnemyForFirebase(enemy.getId(), enemy.getLocation()));
        }

        return enemyForFirebases;
    }

    public static List<Enemy> convertEnemyForFirebaseList(List<EnemyForFirebase> enemyForFirebaseList) {
        List<Enemy> enemyList = new ArrayList<>();
        for (EnemyForFirebase enemyForFirebase : enemyForFirebaseList) {
            Enemy enemy = new Enemy();
            enemy.setId(enemyForFirebase.getId());
            enemy.setLocation(enemyForFirebase.getLocation());
            enemyList.add(enemy);
        }
        return enemyList;
    }

    public static List<PlayerForFirebase> convertPlayerList(List<Player> players) {
        ArrayList<PlayerForFirebase> playerList = new ArrayList<>();
        for (Player player : players) {
            PlayerForFirebase playerForFirebase = playerToPlayerForFirebase(player);
            playerList.add(playerForFirebase);
        }

        return playerList;
    }

    public static Player playerForFirebaseToPlayer(PlayerForFirebase playerForFirebase) {
        double longitude = playerForFirebase.getLongitude();
        double latitude = playerForFirebase.getLatitude();
        double aoeRadius = playerForFirebase.getAoeRadius();
        String username = playerForFirebase.getUsername();
        String email = playerForFirebase.getEmail();

        return new Player(longitude, latitude, aoeRadius, username, email);
    }

    public static ItemsForFirebase convertItems(Map<String, Integer> items) {
        ItemsForFirebase itemsForFirebase = new ItemsForFirebase();
        itemsForFirebase.setItemsMap(items);
        itemsForFirebase.setDate(new Date(System.currentTimeMillis()));

        return itemsForFirebase;
    }

    public static List<ItemBoxForFirebase> convertItemBoxMap(Map<String, ItemBox> itemBoxMap) {
        List<ItemBoxForFirebase> itemBoxForFirebaseList = new ArrayList<>();
        for (Map.Entry<String, ItemBox> entry : itemBoxMap.entrySet()) {
            itemBoxForFirebaseList.add(new ItemBoxForFirebase(entry.getKey(), entry.getValue().getLocation(), entry.getValue().isTaken()));
        }
        return itemBoxForFirebaseList;
    }
}
