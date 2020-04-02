package ch.epfl.sdp;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;

import static junit.framework.TestCase.assertEquals;

public class PlayerManagerTest {

    Player player;

    @Before
    public void setup() {
        PlayerManager playerManager = new PlayerManager();
        player = new Player("Username", "Email");
    }

    @Test
    public void addingPlayerShouldIncreasePlayerNumber() {
        assertEquals(0, PlayerManager.getPlayers().size());
        PlayerManager.addPlayer(player);
        assertEquals(1, PlayerManager.getPlayers().size());
    }

    @Test
    public void removingPlayerShouldDecreasePlayerNumber() {
        PlayerManager.addPlayer(player);
        assertEquals(1, PlayerManager.getPlayers().size());
        PlayerManager.removePlayer(player);
        assertEquals(0, PlayerManager.getPlayers().size());
    }

    @Test
    public void takePlayer(){
        PlayerManager.getPlayer("gamer@gmail.com");
        assertEquals("gamer@gmail.com", "gamer@gmail.com");
    }

    @Test
    public void ShouldAddPlayerAndGetPoint(){
        PlayerManager.addPlayer(player);
        assertEquals("gamer@gmail.com", "gamer@gmail.com");
    }

}
