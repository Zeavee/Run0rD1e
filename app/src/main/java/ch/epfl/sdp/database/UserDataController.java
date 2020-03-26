package ch.epfl.sdp.database;

import ch.epfl.sdp.db.LeaderoardViewModel;
import ch.epfl.sdp.entity.Player;

public interface UserDataController {

    void syncCloudFirebaseToRoom(LeaderoardViewModel leaderoardViewModel);

    void storeUser(Player player);
}
