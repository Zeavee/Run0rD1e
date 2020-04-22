package ch.epfl.sdp.database.firebase.api;

import java.util.List;

import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.item.ItemBox;

public class ServerFirestoreDatabaseAPI extends CommonFirestoreDatabaseAPI implements ServerDatabaseAPI {
    @Override
    public void sendEnemies(List<Enemy> enemies) {

    }

    @Override
    public void sendDamage(List<Player> players) {

    }

    @Override
    public void sendItemBox(ItemBox itemBox) {

    }
}
