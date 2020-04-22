package ch.epfl.sdp.database.firebase.api;

import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.firebase.utils.CustumResult;
import ch.epfl.sdp.database.firebase.utils.OnValueReadyCallback;
import ch.epfl.sdp.leaderboard.LeaderboardViewModel;

public interface CommonDatabaseAPI {

    void syncCloudFirebaseToRoom(LeaderboardViewModel leaderboardViewModel);

    void addUser(UserForFirebase userForFirebase, OnValueReadyCallback<CustumResult<Void>> OnValueReadyCallback);

    void fetchUser(String email, OnValueReadyCallback<CustumResult<UserForFirebase>> onValueReadyCallback);

    void selectLobby(OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback);

    void registerToLobby(PlayerForFirebase playerForFirebase, Map<String, Object> data, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback);

    void fetchPlayers(OnValueReadyCallback<CustumResult<List<PlayerForFirebase>>> onValueReadyCallback);
}
