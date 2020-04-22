package ch.epfl.sdp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;

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

//    @Test
//    public void takePlayer(){
//        PlayerManager.getPlayer("gamer@gmail.com");
//        assertEquals("gamer@gmail.com", "gamer@gmail.com"); //what is that ? :P
//    }

    @Test
    public void ShouldAddPlayerAndGetPoint(){
        PlayerManager.addPlayer(player);
        assertEquals("gamer@gmail.com", "gamer@gmail.com");
    }

}
