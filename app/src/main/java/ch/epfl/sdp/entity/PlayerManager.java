package ch.epfl.sdp.entity;

import java.util.ArrayList;

import ch.epfl.sdp.artificial_intelligence.GenPoint;

public class PlayerManager {
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

    public static Player getUser(){
        return user;
    }

    /**
     * Selects the closest player alive from a given position.
     *
     * @return A Player representing the closest player alive from a given position. If there is no
     * player alive it returns null.
     */
    public static Player selectClosestPlayer(GenPoint position) {
        Player target = null;
        double minDistance = Double.MAX_VALUE;
        double currDistance;

        for (Player player : players) {
            currDistance = player.getPosition().toCartesian().distanceFrom(position) - player.getAoeRadius();
            if (currDistance < minDistance && player.isAlive()) {
                minDistance = currDistance;
                target = player;
            }
        }

        return target;
    }
}
