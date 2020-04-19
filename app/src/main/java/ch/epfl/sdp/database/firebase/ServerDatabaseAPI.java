package ch.epfl.sdp.database.firebase;

import java.util.List;

import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.item.ItemBox;

public interface ServerDatabaseAPI extends UserDataController{
    void sendEnemies(List<Enemy> enemies);
    void sendDamage(List<Player> players);
    void sendItemBox(ItemBox itemBox);
}
