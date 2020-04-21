package ch.epfl.sdp.database.firebase;

import java.util.concurrent.CompletableFuture;

import ch.epfl.sdp.leaderboard.LeaderboardViewModel;

public interface CommonDatabaseAPI {

    void syncCloudFirebaseToRoom(LeaderboardViewModel leaderboardViewModel);

    void addUser(UserForFirebase userForFirebase, OnAddUserCallback onAddUserCallback);

    CompletableFuture<UserForFirebase> fetchUser(String email);

    void joinLobby(PlayerForFirebase playerForFirebase);

    void fetchPlayers();
}
