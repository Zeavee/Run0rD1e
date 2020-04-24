package ch.epfl.sdp.database.firebase.api;

import java.util.List;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.utils.CustomResult;
import ch.epfl.sdp.database.firebase.utils.OnValueReadyCallback;

/**
 * The interface with the method related to the firebase firestore
 */
public interface ClientDatabaseAPI extends CommonDatabaseAPI {
    void sendHealthPoints(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    void clearItemBoxes();

    void sendAoeRadius(PlayerForFirebase playerForFirebase,  OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    void fetchDamage(OnValueReadyCallback<CustomResult<Double>> onValueReadyCallback);

    void fetchEnemies(OnValueReadyCallback<CustomResult<List<EnemyForFirebase>>> onValueReadyCallback);
}
