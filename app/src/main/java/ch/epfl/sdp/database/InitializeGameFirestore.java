package ch.epfl.sdp.database;

import android.app.Activity;

import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.map.MapsActivity;

public class InitializeGameFirestore implements InitializeGame {

    @Override
    public void setGameEnvironment() {
        MapsActivity.currentUserEmail =  MapsActivity.authenticationController.getEmailOfCurrentUser();
        while(MapsActivity.currentUserEmail == null) {}

        //fetch all players from lobby for the first time
        MapsActivity.firestoreUserData.getLobby(MapsActivity.lobbyCollectionName);

        // wait until all data fetched
        while(PlayerManager.getInstance().getMapPlayers().size() != 7) {}

        //initialize the currentUser
        MapsActivity.currentUser = PlayerManager.getInstance().getPlayer(MapsActivity.currentUserEmail);
        Game.addToUpdateList(PlayerManager.getInstance());
    }
}
