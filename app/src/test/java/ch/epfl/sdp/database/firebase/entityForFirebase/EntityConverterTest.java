package ch.epfl.sdp.database.firebase.entityForFirebase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.entities.artificial_intelligence.Behaviour;
import ch.epfl.sdp.entities.enemy.Enemy;
import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.items.Healthpack;
import ch.epfl.sdp.items.Item;
import ch.epfl.sdp.items.ItemBox;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class EntityConverterTest {
    @Test
    public void playerConverterTest() {
        UserForFirebase userForFirebase = new UserForFirebase("test@gmail.com", "test", 0);

        Player player = EntityConverter.userForFirebaseToPlayer(userForFirebase);

        PlayerForFirebase playerForFirebase = EntityConverter.playerToPlayerForFirebase(player);
        Player covertBackToPlayer = EntityConverter.playerForFirebaseToPlayer(playerForFirebase);

        assertEquals(userForFirebase.getEmail(), playerForFirebase.getEmail());
        assertEquals(userForFirebase.getUsername(), playerForFirebase.getUsername());
        assertEquals(player.getEmail(), covertBackToPlayer.getEmail());

        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        List<PlayerForFirebase> playerForFirebaseList = EntityConverter.convertPlayerList(playerList);
        assertEquals(1, playerForFirebaseList.size());
    }

    @Test
    public void convertEnemyListTest() {
        List<Enemy> enemyList = new ArrayList<>();
        Enemy enemy = new Enemy();
        enemy.setId(0);
        enemy.setLocation(new GeoPoint(22,22));
        enemyList.add(enemy);

        List<EnemyForFirebase> enemyForFirebaseList = EntityConverter.convertEnemyList(enemyList);
        assertEquals(1, enemyForFirebaseList.size());
        assertEquals(enemyForFirebaseList.get(0).getLocation().getLatitude(), enemy.getLocation().getLatitude(), 0.01);
    }

    @Test
    public void convertEnemyForFirebaseListTest() {
        List<EnemyForFirebase> enemyForFirebasesList = new ArrayList<>();
        enemyForFirebasesList.add(new EnemyForFirebase(1, Behaviour.WAIT, new GeoPointForFirebase(33,33),0));

        List<Enemy> enemyList = EntityConverter.convertEnemyForFirebaseList(enemyForFirebasesList);
        assertEquals(1, enemyList.size());
    }

    @Test
    public void convertItemsTest() {
        Map<String,Integer> items = new HashMap<>();
        Item healthpack = new Healthpack(10);
        items.put(healthpack.getName(), 1);

        ItemsForFirebase itemsForFirebase = EntityConverter.convertItems(items);
        assertTrue(itemsForFirebase.getItemsMap().containsKey(healthpack.getName()));
    }

    @Test
    public void convertItemBoxMap() {
        ItemBox itembox1 = new ItemBox(new GeoPoint(22, 22));
        Item healthPack1 = new Healthpack(10);
        itembox1.putItems(healthPack1, 1);

        ItemBox itembox2 = new ItemBox(new GeoPoint(33, 33));
        Item healthPack2 = new Healthpack(10);
        itembox1.putItems(healthPack2, 2);

        Map<String, ItemBox> itemBoxMap = new HashMap<>();
        itemBoxMap.put("itemBox1", itembox1);
        itemBoxMap.put("itemBox2", itembox2);

        List<ItemBoxForFirebase> itemBoxForFirebaseList = EntityConverter.convertItemBoxMap(itemBoxMap);
        assertEquals(2, itemBoxForFirebaseList.size());
    }
}
