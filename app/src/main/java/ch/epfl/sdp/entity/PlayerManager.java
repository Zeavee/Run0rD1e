package ch.epfl.sdp.entity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import ch.epfl.sdp.geometry.CartesianPoint;

/**
 * This class should contain all the players in the current game. It allows to to operations on all
 * the players in current game, or to have access to them at any time. The user should be set at the
 * beginning of each game and all players should be removed at the end of each game.
 */
public class PlayerManager {
    public static final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static final int NUMBER_OF_PLAYERS_IN_LOBBY = 10;
    public static final CollectionReference USER_COLLECTION_REF = firebaseFirestore.collection("AllUsers");
    public static final CollectionReference LOBBY_COLLECTION_REF = firebaseFirestore.collection("Lobbies");
    public static final String PLAYERS_COLLECTION_NAME = "Players";

    private static DocumentReference lobby_doc_ref;
    /**
     * The list of all players in the current game.
     */
    private static ArrayList<Player> players = new ArrayList<>();
    /**
     * The player representing the user in the game.
     */
    private static Player currentUser;

    /**
     * Get the DocumentReference of currentUser's lobby
     *
     * @return The DocumentReference of the currentUser's lobby on Cloud firebase.
     */
    public static DocumentReference getLobby_doc_ref() {
        return lobby_doc_ref;
    }

    /**
     * Set the DocumentReference of the currentUser's lobby
     *
     * @param lobby_doc_ref the DocumentReference of the currentUser's lobby
     */
    public static void setLobby_doc_ref(DocumentReference lobby_doc_ref) {
        PlayerManager.lobby_doc_ref = lobby_doc_ref;
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
    public static ArrayList<Player> getPlayers() {
        return players;
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
