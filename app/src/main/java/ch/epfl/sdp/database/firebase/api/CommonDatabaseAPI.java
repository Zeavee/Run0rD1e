package ch.epfl.sdp.database.firebase.api;

import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;

/**
 * This interface contains the methods related to the Firebase FireStore
 * All the methods in this CommonDatabaseAPI will be used by both Server and Client
 */
public interface CommonDatabaseAPI {
    /**
     * Add the CurrentUser to the Firebase FireStore
     *
     * @param userForFirebase      The instance of the CurrentUser
     * @param OnValueReadyCallback Callback after adding the CurrentUser to the firebase
     */
    void addUser(UserForFirebase userForFirebase, OnValueReadyCallback<CustomResult<Void>> OnValueReadyCallback);

    /**
     * Fetch the specific User from the Firebase FireStore
     *
     * @param email                The email used to identify the specific user
     * @param onValueReadyCallback Callback after fetching the user from the firebase
     */
    void fetchUser(String email, OnValueReadyCallback<CustomResult<UserForFirebase>> onValueReadyCallback);

    /**
     * Select the lobby which has less than the number of players required to play the game
     * If there is no such lobby, the currentUser will create a new one
     *
     * @param onValueReadyCallback Callback after selecting the lobby in the firebase
     */
    void selectLobby(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    /**
     * Register the CurrentUser to a specific lobby in the Firebase FireStore
     *
     * @param playerForFirebase    The instance of the playerForFirebase
     * @param data                 The data to be added to the lobby
     * @param onValueReadyCallback Callback after registering to the lobby in the firebase
     */
    void registerToLobby(PlayerForFirebase playerForFirebase, Map<String, Object> data, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    /**
     * Fetch players in a specific lobby in the Firebase FireStore
     *
     * @param lobbyName            The lobby name in the Firebase FireStore, the lobby is stored as a document contains collections in FireBase FireStore
     * @param onValueReadyCallback Callback after fetching players from the firebase
     */
    void fetchPlayers(String lobbyName, OnValueReadyCallback<CustomResult<List<PlayerForFirebase>>> onValueReadyCallback);

    /**
     * Add a listener to a collection in FireBase FireStore which contains all the users who have used this app
     * The Collection Name is specified in PlayerManager
     *
     * @param onValueReadyCallback Callback when the general score of some users changed
     */
    void generalGameScoreListener(OnValueReadyCallback<CustomResult<List<UserForFirebase>>> onValueReadyCallback);

    /**
     * Update the location of the CurrentUser in the Firebase FireStore
     *
     * @param playerForFirebase The playerForFirebase contains the location to be updated
     */
    void sendUserPosition(PlayerForFirebase playerForFirebase);

}
