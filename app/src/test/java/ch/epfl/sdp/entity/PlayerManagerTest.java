package ch.epfl.sdp.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.geometry.CartesianPoint;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

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
        PlayerManager.removeAll();
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

    @Test
    public void getPlayersTest() {
        PlayerManager.removeAll();
        PlayerManager.addPlayer(player);
        Player player2 = new Player("Username2", "Email2");
        PlayerManager.addPlayer(player2);
        List<Player> pmPlayers = PlayerManager.getPlayers();
        assertTrue(pmPlayers.get(0).equals(player));
        assertTrue(pmPlayers.get(1).equals(player2));
    }

    @Test
    public void emptyPlayersCheck() {
        PlayerManager.removeAll();
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
