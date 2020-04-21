package ch.epfl.sdp.database.firebase.api;

import java.util.List;

import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.item.ItemBox;


public interface ServerDatabaseAPI extends CommonDatabaseAPI {
    void sendEnemies(List<Enemy> enemies);
    void sendDamage(List<Player> players);
    void sendItemBox(ItemBox itemBox);
}
