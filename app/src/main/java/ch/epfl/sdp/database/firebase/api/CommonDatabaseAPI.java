package ch.epfl.sdp.database.firebase.api;

import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.firebase.utils.CustomResult;
import ch.epfl.sdp.database.firebase.utils.OnValueReadyCallback;
import ch.epfl.sdp.leaderboard.LeaderboardViewModel;

/**
 * The interface with the method related to the firebase firestore
 */
public interface CommonDatabaseAPI {

    void syncCloudFirebaseToRoom(LeaderboardViewModel leaderboardViewModel);

    /**
     * Add the CurrentUser to the Firebase Firestore
     * @param userForFirebase The instance of the CurrentUser
     * @param OnValueReadyCallback The callBack method after complete adding the user to the firebase
     */
    void addUser(UserForFirebase userForFirebase, OnValueReadyCallback<CustomResult<Void>> OnValueReadyCallback);

    /**
     * Fetch the specific User
     * @param email The email used to identify the specific user
     * @param onValueReadyCallback The callBack method after complete fetching the user from the firebase
     */
    void fetchUser(String email, OnValueReadyCallback<CustomResult<UserForFirebase>> onValueReadyCallback);

    /**
     * Select the lobby which has less than the number of players required to play the game
     * @param onValueReadyCallback The callBack method after complete selecting the lobby in the firebase
     */
    void selectLobby(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    /**
     * Register the CurrentUser to a specific lobby in the firebase
     * @param playerForFirebase The instance of the playerForFirebase
     * @param data The data to be added to the lobby
     * @param onValueReadyCallback The callBack method after complete registering to the lobby in the firebase
     */
    void registerToLobby(PlayerForFirebase playerForFirebase, Map<String, Object> data, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    /**
     * Fetch players from the firebase
     * @param onValueReadyCallback The callBack method after complete fetching players from the firebase
     */
    void fetchPlayers(OnValueReadyCallback<CustomResult<List<PlayerForFirebase>>> onValueReadyCallback);
}
