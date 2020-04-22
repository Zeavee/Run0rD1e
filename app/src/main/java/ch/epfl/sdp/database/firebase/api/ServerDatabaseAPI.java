package ch.epfl.sdp.database.firebase.api;

import java.util.List;

import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.utils.CustumResult;
import ch.epfl.sdp.database.firebase.utils.OnValueReadyCallback;
import ch.epfl.sdp.item.ItemBox;

public interface ServerDatabaseAPI {

    void sendEnemies(List<EnemyForFirebase> enemies, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback);

    void sendDamage(List<PlayerForFirebase> players, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback);

    void sendItemBox(ItemBox itemBox);
}
