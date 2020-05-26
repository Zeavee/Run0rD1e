package ch.epfl.sdp.game;

import android.util.Log;

import java.util.List;

import ch.epfl.sdp.database.firebase.entity.EntityConverter;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;

import static android.content.ContentValues.TAG;

public interface StartGameController {
    /**
     *  Implemented by Solo, Server and Client and used in GoogleLocationFinder,
     *  After fetching the location of the CurrentUser (instead of the default 0, 0) from device for the first time we start the whole game
     *  For Solo: It means populate the Enemies, itemboxes locally and start the gameThread
     *  For Server: It means populate the Enemies and itemboxes on cloud firebase and start the gameThread
     *  For Client: It means fetch the Enemies and itemboxes from cloud firebase and start the gameThread.
     */
    void start();

    static void addPlayersInPlayerManager(PlayerManager playerManager, List<PlayerForFirebase> players) {
        for (PlayerForFirebase playerForFirebase : players) {
            Player player = EntityConverter.playerForFirebaseToPlayer(playerForFirebase);
            if (!playerManager.getCurrentUser().getEmail().equals(player.getEmail())) {
                playerManager.addPlayer(player);
            }
            Log.d(TAG, "(Server) Getting Player: " + player);
        }
    }
}
