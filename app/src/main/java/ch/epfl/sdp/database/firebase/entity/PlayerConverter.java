package ch.epfl.sdp.database.firebase.entity;

import ch.epfl.sdp.entity.Player;

public class PlayerConverter {
    public static Player UserForFirebaseToPlayer(UserForFirebase userForFirebase) {
        String username = userForFirebase.getUsername();
        String email = userForFirebase.getEmail();
        double score = userForFirebase.getScore();

        Player player = new Player(username, email);
        player.setScore(score);

        return player;
    }

    public static PlayerForFirebase PlayerToPlayerForFirebase(Player player) {
        PlayerForFirebase playerForFirebase = new PlayerForFirebase();

        playerForFirebase.setUsername(player.getUsername());
        playerForFirebase.setEmail(player.getEmail());
        playerForFirebase.setLocation(player.getLocation());
        playerForFirebase.setAoeRadius(player.getAoeRadius());
        playerForFirebase.setHealthPoints(player.getHealthPoints());

        return playerForFirebase;
    }
}
