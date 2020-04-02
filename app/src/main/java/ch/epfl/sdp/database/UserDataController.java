package ch.epfl.sdp.database;

import ch.epfl.sdp.db.AppViewModel;
import ch.epfl.sdp.entity.Player;

public interface UserDataController {

    void syncCloudFirebaseToRoom(AppViewModel appViewModel, String collectionName);

    void storeUser(String collectionName, Player player);

    /**
     * Get the List of players in the lobby for one game round and add them the playerManager
     * @param collectionName the name of the Collection
     */
    void getLobby(String collectionName);
}
