package ch.epfl.sdp.database.firebase.api;

import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;

/**
 * The interface with the method related to the firebase firestore
 */
public interface ClientDatabaseAPI {

    /**
     * Listen to game start
     */
    void listenToGameStart(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    /**
     * Fetch a list of enemies from the Firebase Firestore
     *
     * @param onValueReadyCallback Callback after fetching the list of enemies from the firebase
     */
    void addEnemyListener(OnValueReadyCallback<CustomResult<List<EnemyForFirebase>>> onValueReadyCallback);

    void addItemBoxesListener(OnValueReadyCallback<CustomResult<List<ItemBoxForFirebase>>> onValueReadyCallback);

    void addUserHealthPointsListener(OnValueReadyCallback<CustomResult<Double>> onValueReadyCallback);

    void addUserItemListener(OnValueReadyCallback<CustomResult<Map<String, Integer>>> onValueReadyCallback);

    void sendUserPosition(PlayerForFirebase playerForFirebase);

    void sendUsedItems(ItemsForFirebase itemsForFirebase);
}
