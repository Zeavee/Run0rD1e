package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;
import ch.epfl.sdp.map.GeoPoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class ItemsTest {
    private static GeoPoint A = new GeoPoint(6.14308, 46.21023);
    private static Healthpack healthpack = new Healthpack(A,false, 60);
    private static Shield shield = new Shield(A, false, 40);
    private static Shrinker shrinker = new Shrinker(A, true, 40, 10);
    private static Scan scan = new Scan(A, false, 50, new MockMapApi());

    @Test
    public void healthpackTest() {
        assertFalse(healthpack.isTaken());
        assertEquals(60.0, healthpack.getHealthPackAmount(), 0);
        assertEquals(EntityType.HEALTHPACK, healthpack.getEntityType());
    }

    @Test
    public void ifItemNotInInventoryNothingHappens() {
        Player player = new Player(6.149290, 46.212470, 50,
                "Skyris", "test@email.com"); //player position is in Geneva
        player.getInventory().removeItem("test");
        assertEquals(0, player.getInventory().getItems().size());
    }

    @Test
    public void shieldTest() {
        assertFalse(shield.isTaken());
        assertEquals(40, shield.getShieldTime(), 0);
        shield.takeItem();
        assertTrue(shield.isTaken());
        assertEquals(EntityType.SHIELD, shield.getEntityType());
    }

    @Test
    public void shrinkerTest() {
        assertTrue(shrinker.isTaken());
        assertEquals(40, shrinker.getShrinkTime(), 0);
        assertEquals(10, shrinker.getShrinkingRadius(), 0);
        assertEquals(EntityType.SHRINKER, shrinker.getEntityType());
    }


    @Test
    public void increaseHealth() {
        Player player1 = new Player(6.149290, 46.212470, 50,
                "Skyris", "test@email.com"); //player position is in Geneva
        player1.setHealthPoints(30);
        healthpack.increaseHealthPlayer(player1, 100);
        player1.getInventory().addItem(healthpack.getName());
        assertEquals(90, player1.getHealthPoints(), 0);
        player1.getInventory().useItem(healthpack);
        assertEquals(100, player1.getHealthPoints(), 0);
    }

    @Test
    public void scanTest() {
        String a = scan.getDescription();
        assertEquals("Item that scans the entire map and reveals other players for a short delay", a);
        scan.showAllPlayers();
        assertEquals(EntityType.SCAN, scan.getEntityType());
    }

}