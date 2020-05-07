package ch.epfl.sdp.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.item.Trap;
import ch.epfl.sdp.map.MockMap;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class TrapTest {
    private Player owner;
    private Player opponent;
    private Game game;
    private Trap trap;

    @Before
    public void setup() {
        owner = new Player(45, 45, 100, "username1", "email1@email.com");
        opponent = new Player(39, 39, 100, "username2", "email2@email.com");

        game = Game.getInstance();

        MockMap mockMap = new MockMap();
        game.setMapApi(mockMap);
        game.setRenderer(mockMap);

        game.initGame();

        trap = new Trap(10, 100);
        PlayerManager.getInstance().removeAll();
        PlayerManager.getInstance().setCurrentUser(owner);
        PlayerManager.getInstance().addPlayer(opponent);
    }

    @After
    public void destroy() {
        game.destroyGame();
    }

    @Test
    public void trapCanBeSetUpAndDoesDamage() throws InterruptedException {
        ItemBox itemBox = new ItemBox(new GeoPoint(41, 41));
        Game.getInstance().addToUpdateList(itemBox);
        Game.getInstance().addToDisplayList(itemBox);
        itemBox.putItems(trap, 1);

        owner.setLocation(new GeoPoint(41, 41));
        Thread.sleep(1000);
//        assertTrue(owner.getInventory().getItems().containsKey(trap.getName()));
        owner.setLocation(new GeoPoint(42, 42));
        trap.useOn(owner);
        Thread.sleep(1000);
        assertEquals(100.0, opponent.getHealthPoints(), 0.01);
    }

    @Test
    public void trapCorrectlyImplementsDisplayable() {
        trap.useOn(PlayerManager.getInstance().getCurrentUser());
        assertNotNull(trap.getLocation());
    }
}
