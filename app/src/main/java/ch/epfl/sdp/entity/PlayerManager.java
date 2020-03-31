package ch.epfl.sdp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private static Map<String, Player> players;

    public PlayerManager() {
        players = new HashMap<>();
    }

    public static void addPlayer(Player player) {
        players.put(player.getEmail(), player);
    }

    public static void removePlayer(Player player) {
        if(players.containsKey(player.getEmail())) {
            players.remove(player.getEmail());
        }
    }

    public static ArrayList<Player> getPlayers() {
        ArrayList<Player> resultPlayers = new ArrayList<>();
        for(Map.Entry<String, Player> entry: players.entrySet()){
            resultPlayers.add(entry.getValue());
        }
        return resultPlayers;
    }

    public static Player getPlayer(String email) {
        return players.get(email);
    }
}
