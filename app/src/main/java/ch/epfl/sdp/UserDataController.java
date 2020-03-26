package ch.epfl.sdp;

import ch.epfl.sdp.db.LeaderoardViewModel;

public interface UserDataController {

    void syncCloudFirebaseToRoom(LeaderoardViewModel leaderoardViewModel);

    void storeUser(Player player);
}
