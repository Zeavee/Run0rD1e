package ch.epfl.sdp.database.firebase.api;

import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;

/**
 * The interface with the method related to the Firebase Firestore
 */
public interface CommonDatabaseAPI {
    /**
     * Add the CurrentUser to the Firebase Firestore
     *
     * @param userForFirebase      The instance of the CurrentUser
     * @param OnValueReadyCallback Callback after adding the CurrentUser to the firebase
     */
    void addUser(UserForFirebase userForFirebase, OnValueReadyCallback<CustomResult<Void>> OnValueReadyCallback);

    /**
     * Fetch the specific User from the Firebase Firestore
     *
     * @param email                The email used to identify the specific user
     * @param onValueReadyCallback Callback after fetching the user from the firebase
     */
    void fetchUser(String email, OnValueReadyCallback<CustomResult<UserForFirebase>> onValueReadyCallback);

    /**
     * Select the lobby which has less than the number of players required to play the game
     *
     * @param onValueReadyCallback Callback after selecting the lobby in the firebase
     */
    void selectLobby(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    /**
     * Register the CurrentUser to a specific lobby in the Firebase Firestore
     *
     * @param playerForFirebase    The instance of the playerForFirebase
     * @param data                 The data to be added to the lobby
     * @param onValueReadyCallback Callback after registering to the lobby in the firebase
     */
    void registerToLobby(PlayerForFirebase playerForFirebase, Map<String, Object> data, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    /**
     * Fetch players from the Firebase Firestore
     *
     * @param onValueReadyCallback Callback after fetching players from the firebase
     */
    void fetchPlayers(String lobbyName, OnValueReadyCallback<CustomResult<List<PlayerForFirebase>>> onValueReadyCallback);

    void generalGameScoreListener(OnValueReadyCallback<CustomResult<List<UserForFirebase>>> onValueReadyCallback);

    void sendUserPosition(PlayerForFirebase playerForFirebase);

}
