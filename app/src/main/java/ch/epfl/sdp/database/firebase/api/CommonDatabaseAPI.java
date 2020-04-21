package ch.epfl.sdp.database.firebase.api;

import ch.epfl.sdp.database.firebase.callback.OnAddUserCallback;
import ch.epfl.sdp.database.firebase.callback.OnValueReadyCallback;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.leaderboard.LeaderboardViewModel;

public interface CommonDatabaseAPI {

    void syncCloudFirebaseToRoom(LeaderboardViewModel leaderboardViewModel);

    void addUser(UserForFirebase userForFirebase, OnAddUserCallback onAddUserCallback);

    void fetchUser(String email, OnValueReadyCallback<UserForFirebase> onValueReadyCallback);

    void joinLobby(PlayerForFirebase playerForFirebase, OnValueReadyCallback<Boolean> onValueReadyCallback);

    void fetchPlayers();
}
