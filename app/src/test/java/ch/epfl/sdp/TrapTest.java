package ch.epfl.sdp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.item.Trap;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class TrapTest {
    private Player owner;
    private Player opponent;
    private Game game;
    private Trap trap;

    @Before
    public void setup() {
        owner = new Player(45, 45, 100, "username1", "email1@email.com");
        opponent = new Player(39, 39, 100, "username2", "email2@email.com");

        MockMapApi mockMapApi = new MockMapApi();
        MapsActivity.setMapApi(mockMapApi);
        mockMapApi.setCurrentLocation(owner.getLocation());

        game = new Game();
        game.initGame();

        trap = new Trap(10, 100);

        PlayerManager.setUser(owner);
        PlayerManager.addPlayer(opponent);
    }

    @After
    public void destroy() {
        game.destroyGame();
    }

    @Test
    public void trapCanBeSetUpAndDoesDamage() throws InterruptedException {
        ItemBox itemBox = new ItemBox();
        Game.addToUpdateList(itemBox);
        Game.addToDisplayList(itemBox);
        itemBox.putItems(trap, 1);
        itemBox.setLocation(new GeoPoint(41, 41));

        owner.setLocation(new GeoPoint(41, 41));
        Thread.sleep(1000);
        assertTrue(owner.getInventory().getItems().containsKey(trap));
        owner.setLocation(new GeoPoint(42, 42));
        trap.use();
        Thread.sleep(1000);
        assertEquals(100.0, opponent.getHealthPoints(), 0.01);
        opponent.setLocation(new GeoPoint(42, 42));
        Thread.sleep(1000);
        assertEquals(90.0, opponent.getHealthPoints(), 0.01);
        assertEquals(100.0, owner.getHealthPoints(), 0.01);
    }

    @Test
    public void trapCorrectlyImplementsDisplayable() {
        assertTrue(trap.once());
        assertEquals(EntityType.TRAP, trap.getEntityType());
        trap.use();
        assertNotNull(trap.getLocation());
    }
}
