package ch.epfl.sdp.entity;

import java.util.ArrayList;
import java.util.Map;

import ch.epfl.sdp.MainActivity;
import ch.epfl.sdp.artificial_intelligence.Updatable;

public class PlayerManager implements Updatable {
    private static PlayerManager instance;
    private Map<String, Player> players;


    private PlayerManager() { }

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

    public static PlayerManager getInstance () {
        if (PlayerManager.instance == null) {
            PlayerManager.instance = new PlayerManager ();
        }
        return PlayerManager.instance;
    }

    public Player getPlayer(String email) {
        return players.get(email);
    }

    @Override
    public void update() {
        MainActivity.firebaseUserData.storeUser(MainActivity.lobbyCollectionName, getPlayer(MainActivity.currentUserEmail));
        MainActivity.firebaseUserData.getLobby(MainActivity.lobbyCollectionName);
    }

}
