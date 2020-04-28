package ch.epfl.sdp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.geometry.CartesianPoint;

/**
 * This class should contain all the players in the current game. It allows to to operations on all
 * the players in current game, or to have access to them at any time. The user should be set at the
 * beginning of each game and all players should be removed at the end of each game.
 */
public class PlayerManager {
    public static final int NUMBER_OF_PLAYERS_IN_LOBBY = 10;
    public static final String USER_COLLECTION_NAME = "AllUsers";
    public static final String LOBBY_COLLECTION_NAME = "Lobbies";
    public static final String PLAYER_COLLECTION_NAME = "Players";
    public static final String ENEMY_COLLECTION_NAME = "Enemies";

    private static String lobbyDocumentName;
    private static long numPlayersBeforeJoin;
    private static boolean isServer;

    private static Map<String, Player> playersMap = new HashMap();

    public static void updatePlayersPosition(List<PlayerForFirebase> players){
        for (PlayerForFirebase player: players) {
            playersMap.get(player.getEmail()).setLocation(player.getLocation());
        }
    }

    /**
     * The list of all players in the current game.
     */
    private static List<Player> players = new ArrayList<>();
    /**
     * The player representing the user in the game.
     */
    private static Player currentUser;

    /**
     * Get the DocumentReference of currentUser's lobby
     *
     * @return The DocumentReference of the currentUser's lobby on Cloud firebase.
     */
    public static String getLobbyDocumentName() {
        return lobbyDocumentName;
    }

    /**
     * Set the DocumentReference of the currentUser's lobby
     *
     * @param lobby_document_ref the DocumentReference of the currentUser's lobby
     */
    public static void setLobbyDocumentName(String lobby_document_ref) {
        PlayerManager.lobbyDocumentName = lobby_document_ref;
    }

    public static long getNumPlayersBeforeJoin() {
        return numPlayersBeforeJoin;
    }

    public static void setNumPlayersBeforeJoin(long numPlayersBeforeJoin) {
        PlayerManager.numPlayersBeforeJoin = numPlayersBeforeJoin;
    }

    public static boolean isServer() {
        return isServer;
    }

    public static void setIsServer(boolean isServer) {
        PlayerManager.isServer = isServer;
    }

    /**
     * Add the specified player to the player manager. The player will stay until it is removed by
     * removePlayer() or removeAll().
     *
     * @param player A player to be stored into the player manager.
     */
    public static void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Remove the specified player from the player manager if it exists.
     *
     * @param player A player to be removed from the player manager.
     */
    public static void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * Gets a list of all players in the player manager.
     *
     * @return A list of all players in the player manager
     */
    public static List<Player> getPlayers() {
        return players;
    }

    /**
     * Set a list of all players in the player manager
     *
     * @param players A list of players to be added
     */
    public static void setPlayers(List<Player> players) {
        PlayerManager.players = players;
    }

    /**
     * Remove all the players in the player manager.
     */
    public static void removeAll() {
        players.clear();
    }

    /**
     * Gets the player representing the user in the game.
     *
     * @return A player representing the user in the game.
     */
    public static Player getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the player representing the user in the game.
     *
     * @param player A player representing the user in the game.
     */
    public static void setCurrentUser(Player player) {
        currentUser = player;
        addPlayer(currentUser);
    }

    /**
     * Selects the closest player alive based on a given position in the 2D plane.
     *
     * @return A player representing the closest player alive from a given position. If there is no
     * player alive it returns null.
     */
    public static Player selectClosestPlayer(CartesianPoint position) {
        Player target = null;
        double minDistance = Double.MAX_VALUE;
        double currDistance;

        for (Player player : players) {
            currDistance = player.getPosition().distanceFrom(position) - player.getAoeRadius();
            if (currDistance < minDistance && player.isAlive()) {
                minDistance = currDistance;
                target = player;
            }
        }

        return target;
    }
}