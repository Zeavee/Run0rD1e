package ch.epfl.sdp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.map.MapsActivity;

public class PlayerManager implements Updatable {
    private static PlayerManager instance;
    private static Map<String, Player> players = new HashMap<>();


    public PlayerManager() { }

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

    public Map<String, Player> getMapPlayers() {
        return players;
    }

    public static PlayerManager getInstance () {
        if (PlayerManager.instance == null) {
            PlayerManager.instance = new PlayerManager ();
        }
        return PlayerManager.instance;
    }

    public static Player getPlayer(String email) {
        return players.get(email);
    }

    @Override
    public void update() {
        MapsActivity.firestoreUserData.storeUser(MapsActivity.lobbyCollectionName, MapsActivity.currentUser);
        MapsActivity.firestoreUserData.getLobby(MapsActivity.lobbyCollectionName);
    }

}
