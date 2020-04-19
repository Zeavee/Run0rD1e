package ch.epfl.sdp.database.firebase;

import ch.epfl.sdp.db.LeaderoardViewModel;
import ch.epfl.sdp.entity.Player;

public interface CommonDatabaseAPI {

    void syncCloudFirebaseToRoom(LeaderoardViewModel leaderoardViewModel);

    void addUser(UserForFirebase userForFirebase, OnAddUserCallback onAddUserCallback);

    void joinLobby(Player player);

    void fetchPlayers();
}
