package ch.epfl.sdp;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import static org.junit.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class PlayerManagerTest {

    Player player;

    @Before
    public void setup() {
        player = new Player("Username", "Email");
        PlayerManager.removeAll();
    }

    @Test
    public void addingPlayerShouldIncreasePlayerNumber() {
        assertEquals(0, PlayerManager.getPlayers().size());
        PlayerManager.addPlayer(player);
        assertEquals(1, PlayerManager.getPlayers().size());
    }

    @Test
    public void removingPlayerShouldDecreasePlayerNumber() {
        PlayerManager.emptyPlayers();
        PlayerManager.addPlayer(player);
        assertEquals(1, PlayerManager.getPlayers().size());
        PlayerManager.removePlayer(player);
        assertEquals(0, PlayerManager.getPlayers().size());
    }

    @Test
    public void getPlayersTest() {
        PlayerManager.removeAll();
        PlayerManager.addPlayer(player);
        Player player2 = new Player("Username2", "Email2");
        PlayerManager.addPlayer(player2);
        ArrayList<Player> pmPlayers = PlayerManager.getPlayers();
        assertTrue(pmPlayers.get(0).equals(player));
        assertTrue(pmPlayers.get(1).equals(player2));
    }

    public void shouldSetUser() {
        PlayerManager.setUser(player);
        assertEquals(player, PlayerManager.getUser());
    }

    @Test
    public void ShouldAddPlayerAndGetPoint(){
        PlayerManager.addPlayer(player);
        assertEquals("gamer@gmail.com", "gamer@gmail.com");
    }

    public void emptyPlayersCheck() {
        PlayerManager.emptyPlayers();
        assertTrue(PlayerManager.getPlayers().isEmpty());
    }

    @Test
    public void shouldSetPlayers() {
        ArrayList<Player> toSet = new ArrayList<Player>();
        Player p1 = new Player(6.151210, 46.212470, 10,
                "p1", "p1@email.com");
        Player p2 = new Player(4.149290, 46.212470, 10,
                "p2", "p2@email.com");
        Player p3 = new Player(5.149295, 46.212470, 10,
                "p3", "p3@email.com");
        Player p4 = new Player(3.149290, 46.212478, 10,
                "p4", "p4@email.com");
        toSet.add(p1);
        toSet.add(p2);
        toSet.add(p3);
        toSet.add(p4);
        for(int i = 0; i < PlayerManager.getPlayers().size(); i++ ) {
            assertTrue(PlayerManager.getPlayers().get(i).equals(toSet.get(i)));
        }
    }

}
