package ch.epfl.sdp;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.CartesianPoint;

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
        PlayerManager.addPlayer(player);
        assertEquals(1, PlayerManager.getPlayers().size());
        PlayerManager.removePlayer(player);
        assertEquals(0, PlayerManager.getPlayers().size());
    }

    @Test
    public void testSetAndGet(){
        assertEquals(10, PlayerManager.NUMBER_OF_PLAYERS_IN_LOBBY);
        assertEquals("AllUsers", PlayerManager.USER_COLLECTION_NAME);
        assertEquals("Lobbies", PlayerManager.LOBBY_COLLECTION_NAME);
        assertEquals("Players", PlayerManager.PLAYER_COLLECTION_NAME);
        assertEquals("Enemies", PlayerManager.ENEMY_COLLECTION_NAME);

        PlayerManager.setNumPlayersBeforeJoin(3);
        assertEquals(3, PlayerManager.getNumPlayersBeforeJoin());

        PlayerManager.setLobbyDocumentName("test");
        assertEquals("test", PlayerManager.getLobbyDocumentName());

        PlayerManager.setIsServer(false);
        assertEquals(false, PlayerManager.isServer());

        PlayerManager.setCurrentUser(new Player("test", "test@gmail.com"));
        assertEquals("test", PlayerManager.getCurrentUser().getUsername());

        PlayerManager.removeAll();
        PlayerManager.addPlayer(new Player("test1", "test1@gmail.com"));
        PlayerManager.addPlayer(new Player("test2", "test2@gmail.com"));
        List<Player> players = PlayerManager.getPlayers();
        assertEquals(2, players.size());
    }

    @Test
    public void testSelectCloestPlayer() {
        PlayerManager.removeAll();
        Player player1 = new Player("test", "test@gmail.com");
        player1.setPosition(new CartesianPoint(1,1));
        PlayerManager.addPlayer(player1);

        Player closestPlayer = PlayerManager.selectClosestPlayer(new CartesianPoint(1,1));
        assertEquals("test", closestPlayer.getUsername());
    }
}
