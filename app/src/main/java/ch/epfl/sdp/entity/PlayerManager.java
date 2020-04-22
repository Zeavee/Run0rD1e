package ch.epfl.sdp.entity;

import java.util.ArrayList;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.map.MapsActivity;

public class PlayerManager  implements Updatable {
    private static PlayerManager instance;
    private static ArrayList<Player> players = new ArrayList<>();
    public static final int NUMBER_OF_PLAYERS_IN_Lobby = 10;
    public static final String LOBBY_PATH = "Lobbies";
    public static final String PLAYERS_PATH = "Players";
    private static Player user;

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

    public static void setUser(Player player){
        user = player;
        addPlayer(user);
    }

    public static PlayerManager getInstance () {
        if (PlayerManager.instance == null) {
            PlayerManager.instance = new PlayerManager ();
        }
        return PlayerManager.instance;
    }

    public static Player getUser(){
        return user;
    }

    @Override
    public void update() {
            MapsActivity.firestoreUserData.storeUser(MapsActivity.lobbyCollectionName, getUser());
            MapsActivity.firestoreUserData.getLobby(MapsActivity.lobbyCollectionName);
        }

    public static void emptyPlayers() {
        players = new ArrayList<Player>();
    }

    public static void setPlayers(ArrayList<Player> players) {
        PlayerManager.players = players;
    }
}