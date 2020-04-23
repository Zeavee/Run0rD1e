package ch.epfl.sdp.database.firebase.entity;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.artificial_intelligence.Enemy;
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
    public static Player UserForFirebaseToPlayer(UserForFirebase userForFirebase) {
        String username = userForFirebase.getUsername();
        String email = userForFirebase.getEmail();
        double score = userForFirebase.getScore();

        Player player = new Player(username, email);
        player.setScore(score);

        return player;
    }

    /**
     * Convert from local in-game Player to Firebase stored in-game Player
     * @param player The local in-game Player
     * @return The firebase stored in-game Player
     */
    public static PlayerForFirebase PlayerToPlayerForFirebase(Player player) {
        PlayerForFirebase playerForFirebase = new PlayerForFirebase();

        playerForFirebase.setUsername(player.getUsername());
        playerForFirebase.setEmail(player.getEmail());
        playerForFirebase.setLocation(player.getLocation());
        playerForFirebase.setAoeRadius(player.getAoeRadius());
        playerForFirebase.setHealthPoints(player.getHealthPoints());

        return playerForFirebase;
    }

    /**
     * Convert from local in-game Enemy to Firebase stored in-game Enemy
     * @param enemies The list of local in-game Enemy
     * @return The list of Firebase stored in-game Enemy
     */
    public static List<EnemyForFirebase> EnemyToEnemyForFirebase(List<Enemy> enemies) {
        List<EnemyForFirebase> enemyForFirebases = new ArrayList<>();
        for(Enemy enemy: enemies) {
            enemyForFirebases.add(new EnemyForFirebase(enemy.getLocation()));
        }

        return enemyForFirebases;
    }
}