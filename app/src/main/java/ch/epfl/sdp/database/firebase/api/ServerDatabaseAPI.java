package ch.epfl.sdp.database.firebase.api;

import java.util.List;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;

/**
 * The interface with the method related to the firebase firestore
 */
public interface ServerDatabaseAPI {
    /**
     * Send the enemies to the Firebase Firestore
     *
     * @param enemies              A list of enemies
     * @param onValueReadyCallback Callback after sending the enemies to the firebase
     */
    void sendEnemies(List<EnemyForFirebase> enemies, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    void sendDamage(List<PlayerForFirebase> players, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    /**
     * Listen to the number of players in the lobby, if the lobby is full, populate the enemy and start the game
     * @param onValueReadyCallback Callback after the lobby is full
     */
    void listenToNumOfPlayers(OnValueReadyCallback<CustomResult<Boolean>> onValueReadyCallback);

    /**
     * Start the game when the lobby is full and the environment is populated.
     */
    void startGame();
}
