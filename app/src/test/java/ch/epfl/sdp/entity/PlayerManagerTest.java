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
        PlayerManager.getInstance().removeAll();
    }

    @Test
    public void addingPlayerShouldIncreasePlayerNumber() {
        assertEquals(0, PlayerManager.getInstance().getPlayers().size());
        PlayerManager.getInstance().addPlayer(player);
        assertEquals(1, PlayerManager.getInstance().getPlayers().size());
    }

    @Test
    public void removingPlayerShouldDecreasePlayerNumber() {
        PlayerManager.getInstance().removeAll();
        PlayerManager.getInstance().addPlayer(player);
        assertEquals(1, PlayerManager.getInstance().getPlayers().size());
        PlayerManager.getInstance().getInstance().removePlayer(player);
        assertEquals(0, PlayerManager.getInstance().getPlayers().size());
    }

    @Test
    public void testSetAndGet(){
        assertEquals(2, PlayerManager.getInstance().NUMBER_OF_PLAYERS_IN_LOBBY);
        assertEquals("AllUsers", PlayerManager.getInstance().USER_COLLECTION_NAME);
        assertEquals("Lobbies", PlayerManager.getInstance().LOBBY_COLLECTION_NAME);
        assertEquals("Players", PlayerManager.getInstance().PLAYER_COLLECTION_NAME);
        assertEquals("Enemies", PlayerManager.getInstance().ENEMY_COLLECTION_NAME);

        PlayerManager.getInstance().setNumPlayersBeforeJoin(3);
        assertEquals(3, PlayerManager.getInstance().getNumPlayersBeforeJoin());

        PlayerManager.getInstance().setLobbyDocumentName("test");
        assertEquals("test", PlayerManager.getInstance().getLobbyDocumentName());

        PlayerManager.getInstance().setIsServer(false);
        assertEquals(false, PlayerManager.getInstance().isServer());

        PlayerManager.getInstance().setCurrentUser(new Player("test", "test@gmail.com"));
        assertEquals("test", PlayerManager.getInstance().getCurrentUser().getUsername());

        PlayerManager.getInstance().removeAll();
        PlayerManager.getInstance().addPlayer(new Player("test1", "test1@gmail.com"));
        PlayerManager.getInstance().addPlayer(new Player("test2", "test2@gmail.com"));
        List<Player> players = PlayerManager.getInstance().getPlayers();
        assertEquals(2, players.size());
    }

    @Test
    public void testSelectCloestPlayer() {
        PlayerManager.getInstance().removeAll();
        Player player1 = new Player("test", "test@gmail.com");
        player1.setPosition(new CartesianPoint(1,1));
        PlayerManager.getInstance().addPlayer(player1);

        Player closestPlayer = PlayerManager.getInstance().selectClosestPlayer(new CartesianPoint(1,1));
        assertEquals("test", closestPlayer.getUsername());
    }

    @Test
    public void getPlayersTest() {
        PlayerManager.getInstance().removeAll();
        PlayerManager.getInstance().addPlayer(player);
        Player player2 = new Player("Username2", "Email2");
        PlayerManager.getInstance().addPlayer(player2);
        List<Player> pmPlayers = PlayerManager.getInstance().getPlayers();
        assertTrue(pmPlayers.get(0).equals(player));
        assertTrue(pmPlayers.get(1).equals(player2));
    }

    @Test
    public void emptyPlayersCheck() {
        PlayerManager.getInstance().removeAll();
        assertTrue(PlayerManager.getInstance().getPlayers().isEmpty());
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
        for(int i = 0; i < PlayerManager.getInstance().getPlayers().size(); i++ ) {
            assertTrue(PlayerManager.getInstance().getPlayers().get(i).equals(toSet.get(i)));
        }
    }

    @Test
    public void lobbyDocumentNameGetterSetterWorks() {
        PlayerManager.getInstance().setLobbyDocumentName("abc");
        assertEquals("abc", PlayerManager.getInstance().getLobbyDocumentName());
    }

    @Test
    public void setIsServerShouldWork() {
        PlayerManager.getInstance().setIsServer(true);
        assertTrue(PlayerManager.getInstance().isServer());
    }
}
