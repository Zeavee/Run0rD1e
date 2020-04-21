package ch.epfl.sdp.entity;

import java.util.ArrayList;

import ch.epfl.sdp.geometry.CartesianPoint;

/**
 * This class should contain all the players in the current game. It allows to to operations on all
 * the players in current game, or to have access to them at any time. The user should be set at the
 * beginning of each game and all players should be removed at the end of each game.
 */
public class PlayerManager {
    /**
     * The list of all players in the current game.
     */
    private static ArrayList<Player> players = new ArrayList<>();
    /**
     * The player representing the user in the game.
     */
    private static Player currentUser;

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
