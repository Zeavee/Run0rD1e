package ch.epfl.sdp.entity;

import java.util.ArrayList;

public class PlayerManager {
    private static ArrayList<Player> players;
    public static final int NUMBER_OF_PLAYERS_IN_Lobby = 10;
    public static final String LOBBY_PATH = "Lobbies";
    public static final String PLAYERS_PATH = "Players";

    public PlayerManager() {
        players = new ArrayList<>();
    }

    public static void addPlayer(Player player) {
        players.add(player);
    }

    public static void removePlayer(Player player) {
        players.remove(player);
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }
}
