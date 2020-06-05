package ch.epfl.sdp.database.firebase.api;

import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.entityForFirebase.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.UserForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.geometry.Area;

/**
 * The interface with the method related to the firebase fireStore
 */
public interface ServerDatabaseAPI {
    /**
     * Set the name of the lobby document in Firebase FireStore
     *
     * @param lobbyName The name of the lobby document in Firebase FireStore
     */
    void setLobbyRef(String lobbyName);

    /**
     * Listen to the number of players in the lobby. Callback when the lobby is full and
     * then can do the following steps such as populate the enemy, itemBoxes... and start the game
     * by calling the corresponding methods.
     *
     * @param onValueReadyCallback Callback after the lobby is full
     */
    void listenToNumOfPlayers(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    /**
     * Start the game by set the field 'startGame' to be true in the lobby document in Firebase FireStore
     *
     * @param onValueReadyCallback Callback after the startGame field is set to be true
     */
    void startGame(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    /**
     * Fetch the generalScore of all the players in this lobby before the start of the game
     *
     * @param playerEmailList      The list of emails whose generalScore we will fetch
     * @param onValueReadyCallback Callback after the players generalScore has been fetched
     */
    void fetchGeneralScoreForPlayers(List<String> playerEmailList, OnValueReadyCallback<CustomResult<List<UserForFirebase>>> onValueReadyCallback);

    /**
     * Send the enemies to the Firebase FireStore
     *
     * @param enemies A list of EnemyForFirebase
     */
    void sendEnemies(List<EnemyForFirebase> enemies);

    /**
     * Add a listener to collection in Firebase FireStore
     *
     * @param tClass               Possible values EnemyForFirebase.class / ItemBoxForFirebase.class / PlayerForFirebase.class
     * @param collectionName       corresponding collectionName of tClass
     * @param onValueReadyCallback callback when any document in the collection changed
     * @param <T>                  the type of the class modeled by this {@code Class} object.
     */
    <T> void addCollectionListener(Class<T> tClass, String collectionName, OnValueReadyCallback<CustomResult<List<T>>> onValueReadyCallback);

    /**
     * Send the itemBoxes to the Firebase FireStore
     *
     * @param itemBoxForFirebaseList A list of ItemBoxForFirebase
     */
    void sendItemBoxes(List<ItemBoxForFirebase> itemBoxForFirebaseList);

    /**
     * Update the healthPoint of the Players in the lobby
     *
     * @param playerForFirebases A list of playerForFirebase
     */
    void sendPlayersHealth(List<PlayerForFirebase> playerForFirebases);

    /**
     * Send the items taken by the players in the current game round to the Firebase FireStore
     *
     * @param emailsItemsMap A map from player's email to the items taken by the player with the corresponding email
     */
    void sendPlayersItems(Map<String, ItemsForFirebase> emailsItemsMap);

    /**
     * Add a listener to the usedItems of the players in the current game round, callback when any player uses some items.
     *
     * @param onValueReadyCallback Callback when any player uses some items
     */
    void addUsedItemsListener(OnValueReadyCallback<CustomResult<Map<String, ItemsForFirebase>>> onValueReadyCallback);

    /**
     * Send the game area of the current game round to the FireBase FireStore
     *
     * @param gameArea The game area of the current game round
     */
    void sendGameArea(Area gameArea);

    /**
     * This method cleans the listeners of the API
     */
    void cleanListeners();

    /**
     * Send a signal to be sure that the server is alive.
     * @param signal The signal is a long.
     */
    void sendServerAliveSignal(long signal);

    /**
     * Update the score of the players in the current game round to the Firebase FireStore
     *
     * @param emailsScoreMap A map from player's email to player score
     */
    void updatePlayersCurrentScore(Map<String, Integer> emailsScoreMap);
}
