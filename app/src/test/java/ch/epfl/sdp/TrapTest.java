package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.item.Trap;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TrapTest {
    @Test
    public void trapCanBeSetUpAndDoesDamage() throws InterruptedException {
        Player owner = new Player(45, 45, 100, "username1", "email1@email.com");
        Player opponent = new Player(40, 40, 100, "username2", "email2@email.com");

        MockMapApi mockMapApi = new MockMapApi();
        MapsActivity.setMapApi(mockMapApi);
        mockMapApi.setCurrentLocation(owner.getLocation());

        ItemBox itemBox = new ItemBox();
        Game.addToUpdateList(itemBox);
        Game.addToDisplayList(itemBox);
        Trap trap = new Trap(10, 100);
        itemBox.putItems(trap, 1);
        itemBox.setLocation(new GeoPoint(41, 41));

        PlayerManager.setUser(owner);
        PlayerManager.addPlayer(opponent);
        Game game = new Game();
        game.initGame();

        owner.setLocation(new GeoPoint(41, 41));
        Thread.sleep(1000);
        assertTrue(owner.getInventory().getItems().containsKey(trap.getName()));
        owner.setLocation(new GeoPoint(42, 42));
        trap.use();
        Thread.sleep(1000);
        assertEquals(100, opponent.getHealthPoints());
        opponent.setLocation(new GeoPoint(42, 42));
        Thread.sleep(1000);
        assertEquals(90, opponent.getHealthPoints());
    }
}
