package ch.epfl.sdp.database.utils;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.entity.Player;

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
        playerForFirebase.setLocation(player.getLocation());
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
    public static List<EnemyForFirebase> enemyToEnemyForFirebase(List<Enemy> enemies) {
        List<EnemyForFirebase> enemyForFirebases = new ArrayList<>();
        for (Enemy enemy : enemies) {
            enemyForFirebases.add(new EnemyForFirebase(enemy.getId(), enemy.getLocation()));
        }

        return enemyForFirebases;
    }
}
