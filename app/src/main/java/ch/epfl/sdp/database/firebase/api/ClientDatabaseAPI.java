package ch.epfl.sdp.database.firebase.api;

import java.util.List;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;

/**
 * The interface with the method related to the firebase firestore
 */
public interface ClientDatabaseAPI extends CommonDatabaseAPI {
    void sendHealthPoints(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    void sendAoeRadius(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    void fetchDamage(OnValueReadyCallback<CustomResult<Double>> onValueReadyCallback);

    /**
     * Fetch a list of enemies from the Firebase Firestore
     *
     * @param onValueReadyCallback Callback after fetching the list of enemies from the firebase
     */
    void fetchEnemies(OnValueReadyCallback<CustomResult<List<EnemyForFirebase>>> onValueReadyCallback);

    /**
     * Listen to game start
     */
    void listenToGameStart(OnValueReadyCallback<CustomResult<Boolean>> onValueReadyCallback);
}
