package ch.epfl.sdp.database;

import java.util.List;

import ch.epfl.sdp.db.LeaderoardViewModel;
import ch.epfl.sdp.entity.Player;

public interface UserDataController {

    void syncCloudFirebaseToRoom(LeaderoardViewModel leaderoardViewModel);

    void storeUser(String collectionName, Player player);

    /**
     * Get the List of players in the lobby for one game round and add them the playerManager
     * @param collectionName the name of the Collection
     */
    void getLobby(String collectionName);
}
