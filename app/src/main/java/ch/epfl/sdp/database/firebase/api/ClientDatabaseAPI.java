package ch.epfl.sdp.database.firebase.api;

import java.util.List;

import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.utils.CustumResult;
import ch.epfl.sdp.database.firebase.utils.OnValueReadyCallback;

public interface ClientDatabaseAPI extends CommonDatabaseAPI {
    void sendHealthPoints(List<PlayerForFirebase> players, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback);

    void clearItemBoxes();

    void sendAoeRadius(List<PlayerForFirebase> players, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback);
}
