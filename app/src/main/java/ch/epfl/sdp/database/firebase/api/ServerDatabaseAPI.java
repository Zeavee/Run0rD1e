package ch.epfl.sdp.database.firebase.api;

import java.util.List;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.item.ItemBox;

/**
 * The interface with the method related to the firebase firestore
 */
public interface ServerDatabaseAPI {

    void sendEnemies(List<EnemyForFirebase> enemies, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    void sendDamage(List<PlayerForFirebase> players, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    void sendItemBox(ItemBox itemBox);
}
