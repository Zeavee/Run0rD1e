package ch.epfl.sdp.database.firebase.api;

import java.util.List;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.utils.CustumResult;
import ch.epfl.sdp.database.firebase.utils.OnValueReadyCallback;

public interface ClientDatabaseAPI extends CommonDatabaseAPI {
    void sendHealthPoints(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback);

    void clearItemBoxes();

    void sendAoeRadius(PlayerForFirebase playerForFirebase,  OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback);

    void fetchDamage(OnValueReadyCallback<CustumResult<Double>> onValueReadyCallback);

    void fetchEnemies(OnValueReadyCallback<CustumResult<List<EnemyForFirebase>>> onValueReadyCallback);
}
