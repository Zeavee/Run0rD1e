package ch.epfl.sdp.database.firebase.api;

import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.geometry.Area;

/**
 * The interface with the method related to the firebase firestore
 */
public interface ServerDatabaseAPI {
    void setLobbyRef(String lobbyName);

    /**
     * Listen to the number of players in the lobby, if the lobby is full, populate the enemy and start the game
     *
     * @param onValueReadyCallback Callback after the lobby is full
     */
    void listenToNumOfPlayers(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    /**
     * Start the game when the lobby is full and the environment is populated.
     *
     * @param onValueReadyCallback Callback after the lobby is full
     */
    void startGame(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    void fetchGeneralScoreForPlayers(List<String> playerEmailList,  OnValueReadyCallback<CustomResult<List<UserForFirebase>>> onValueReadyCallback);

    /**
     * Send the enemies to the Firebase Firestore
     *
     * @param enemies A list of enemies
     */
    void sendEnemies(List<EnemyForFirebase> enemies);

    void sendItemBoxes(List<ItemBoxForFirebase> itemBoxForFirebaseList);

    void sendPlayersHealth(List<PlayerForFirebase> playerForFirebases);

    void sendPlayersItems(Map<String, ItemsForFirebase> emailsItemsMap);

    void updatePlayersScore(String scoreType, Map<String, Integer> emailsScoreMap);

    void addUsedItemsListener(OnValueReadyCallback<CustomResult<Map<String, ItemsForFirebase>>> onValueReadyCallback);

    void addPlayersPositionListener(OnValueReadyCallback<CustomResult<List<PlayerForFirebase>>> onValueReadyCallback);

    void sendGameArea(List<Area> gameArea);
}
