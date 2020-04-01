package ch.epfl.sdp.entity;

import java.util.ArrayList;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager instance = new PlayerManager();
    private Map<String, Player> players;

    private PlayerManager() {}

    public static PlayerManager getInstance() {
        return instance;
    }

    public void addPlayer(Player player) {
        players.put(player.getEmail(), player);
    }

    public void removePlayer(Player player) {
        if(players.containsKey(player.getEmail())) {
            players.remove(player.getEmail());
        }
    }

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> resultPlayers = new ArrayList<>();
        for(Map.Entry<String, Player> entry: players.entrySet()){
            resultPlayers.add(entry.getValue());
        }
        return resultPlayers;
    }

    public Player getPlayer(String email) {
        return players.get(email);
    }
}
