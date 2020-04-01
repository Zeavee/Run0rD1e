package ch.epfl.sdp.entity;

import java.util.ArrayList;

public class PlayerManager {
    private static ArrayList<Player> players = new ArrayList<>();

    public static void addPlayer(Player player) {
        players.add(player);
    }

    public static void removePlayer(Player player) {
        players.remove(player);
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void removeAll(){
        players.clear();
    }
}
