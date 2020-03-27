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
}
