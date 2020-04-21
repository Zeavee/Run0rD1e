package ch.epfl.sdp.database.firebase;

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
}
