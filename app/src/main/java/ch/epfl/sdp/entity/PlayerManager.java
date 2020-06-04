package ch.epfl.sdp.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.geometry.GeoPoint;

/**
 * This class should contain all the players in the current game. It allows to to operations on all
 * the players in current game, or to have access to them at any time. The user should be set at the
 * beginning of each game and all players should be removed at the end of each game.
 */
public class PlayerManager {

    /**
     * Represents the number of players in the lobby (i.e. the total number of players withing a single game)
     */
    public static final int NUMBER_OF_PLAYERS_IN_LOBBY = 2;

    public static final String USER_COLLECTION_NAME = "AllUsers";
    public static final String LOBBY_COLLECTION_NAME = "Lobbies";
    public static final String PLAYER_COLLECTION_NAME = "Players";
    public static final String ENEMY_COLLECTION_NAME = "Enemies";
    public static final String ITEM_COLLECTION_NAME = "Items";
    public static final String USED_ITEM_COLLECTION_NAME = "UsedItems";
    public static final String GAME_AREA_COLLECTION_NAME = "GameArea";

    private String lobbyDocumentName;
    private long numPlayersInLobby;
    private boolean isServer;
    private boolean isInLobby;
    private boolean soloMode;

    /**
     * The player representing the user in the game.
     */
    private Player currentUser;

    private final Map<String, Player> playersMap = new HashMap<>();

    private final List<Player> playersWaitingItems = new ArrayList<>();

    private final List<Player> playersWaitingHealthPoint = new ArrayList<>();

    private List<Player> playersWaitingAoeRadius = new ArrayList<>();

    private static final PlayerManager instance = new PlayerManager();

    /**
     * This methods returns the instance of the singleton
     *
     * @return the instance of player manager
     */
    public static PlayerManager getInstance() {
        return instance;
    }

    private PlayerManager() {
        lobbyDocumentName = "";
        numPlayersInLobby = 0;
        isInLobby = false;
        isServer = false;
        soloMode = false;
    }

    /**
     * Get the DocumentReference of currentUser's lobby
     *
     * @return The DocumentReference of the currentUser's lobby on Cloud firebase.
     */
    public String getLobbyDocumentName() {
        return lobbyDocumentName;
    }

    /**
     * Set the DocumentReference of the currentUser's lobby
     *
     * @param lobby_document_ref the DocumentReference of the currentUser's lobby
     */
    public void setLobbyDocumentName(String lobby_document_ref) {
        this.lobbyDocumentName = lobby_document_ref;
    }

    /**
     * This method returns the number of players in the lobby
     *
     * @return the number of players in the lobby
     */
    public long getNumPlayersInLobby() {
        return numPlayersInLobby;
    }

    /**
     * This method sets the number of players in the lobby
     *
     * @param numPlayersInLobby the number of players in the lobby
     */
    public void setNumPlayersInLobby(long numPlayersInLobby) {
        this.numPlayersInLobby = numPlayersInLobby;
    }

    /**
     * This method tells if the player acts as the server
     *
     * @return a boolean that tells if the player acts as the server
     */
    public boolean isServer() {
        return isServer;
    }

    /**
     * This method sets the boolean that tells if the player act as the server
     *
     * @param isServer the value of the boolean we want to use
     */
    public void setIsServer(boolean isServer) {
        this.isServer = isServer;
    }

    /**
     * Check whether the play mode is soloMode or multiPlayer mode
     *
     * @return True if the play mode is soloMode else false
     */
    public boolean isSoloMode() {
        return soloMode;
    }

    /**
     * Set the play mode, true if the play mode is soloMode else false
     *
     * @param soloMode Boolean value indicate whether the play mode is soloMode
     */
    public void setSoloMode(boolean soloMode) {
        this.soloMode = soloMode;
    }

    /**
     * Return the variable that indicates whether or not the player is already in the lobby.
     * @return True iff the player is in the lobby.
     */
    public boolean isInLobby() {
        return isInLobby;
    }

    /**
     * Set the variable that indicates whether or not the player is already in the lobby.
     * @param inLobby The variable that indicates whether or not the player is already in the lobby.
     */
    public void setInLobby(boolean inLobby) {
        isInLobby = inLobby;
    }

    /**
     * Add the specified player to the player manager. The player will stay until it is removed by
     * removePlayer() or removeAll().
     *
     * @param player A player to be stored into the player manager.
     */
    public void addPlayer(Player player) {
        playersMap.put(player.getEmail(), player);
    }

    /**
     * Gets a list of all players in the player manager.
     *
     * @return A list of all players in the player manager
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(playersMap.values());
    }

    /**
     * This method returns the list of all the players in the game sorted by their score in the current game
     *
     * @return the list of all the players in the game sorted by their score in the current game
     */
    public List<Player> getPlayersSortByIngameScore() {
        List<Player> players = this.getPlayers();
        Comparator<Player> compareByIngameScore = (o1, o2) -> o2.getCurrentGameScore() - o1.getCurrentGameScore();
        Collections.sort(players, compareByIngameScore);
        return players;
    }

    /**
     * This method returns the map from emails to players
     *
     * @return the map from emails to players
     */
    public Map<String, Player> getPlayersMap() {
        return playersMap;
    }

    /**
     * Set a list of all players in the player manager
     *
     * @param players A list of players to be added
     */
    public void setPlayers(List<Player> players) {
        playersMap.clear();
        for (Player player : players) {
            playersMap.put(player.getEmail(), player);
        }
    }

    /**
     * This method return the waiting list of the players picked up an item
     *
     * @return the waiting list of the players that picked up an item
     */
    public List<Player> getPlayersWaitingItems() {
        return playersWaitingItems;
    }

    /**
     * This method adds the player that picked up an item in a waiting list
     *
     * @param player the player that picked up an item
     */
    public void addPlayerWaitingItems(Player player) {
        if (!currentUser.getEmail().equals(player.getEmail())) {
            playersWaitingItems.add(player);
        }
    }

    /**
     * This method return the waiting list of the players that had their health points changed
     *
     * @return the waiting list of the players that had their health points changed
     */
    public List<Player> getPlayersWaitingHealthPoint() {
        return playersWaitingHealthPoint;
    }

    /**
     * This method adds the player that had his health points changed in a waiting list
     *
     * @param player the player that had his health points changed
     */
    public void addPlayerWaitingHealth(Player player) {
        playersWaitingHealthPoint.add(player);
    }

    public List<Player> getPlayersWaitingAoeRadius() {
        return playersWaitingAoeRadius;
    }

    public void addPlayerWaitingAoeRadius(Player player) {
        playersWaitingAoeRadius.add(player);
    }


    /**
     * Remove all the players in the player manager.
     */
    public void clear() {
        lobbyDocumentName = "";
        numPlayersInLobby = 0;
        isInLobby = false;
        isServer = false;
        soloMode = false;
        playersMap.clear();
        playersWaitingHealthPoint.clear();
        playersWaitingItems.clear();
        currentUser = null;
    }

    /**
     * Gets the player representing the user in the game.
     *
     * @return A player representing the user in the game.
     */
    public Player getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the player representing the user in the game.
     *
     * @param player A player representing the user in the game.
     */
    public void setCurrentUser(Player player) {
        currentUser = player;
        addPlayer(currentUser);
    }

    /**
     * Selects the closest player alive based on a given position in the 2D plane.
     *
     * @return A player representing the closest player alive from a given position. If there is no
     * player alive (healthPoint is bigger than 0) it returns null.
     */
    public Player selectClosestPlayer(GeoPoint position) {
        Player target = null;
        double minDistance = Double.MAX_VALUE;
        double currDistance;

        for (Player player : playersMap.values()) {
            currDistance = player.getLocation().distanceTo(position) - player.getAoeRadius();
            if (currDistance < minDistance && player.getHealthPoints() > 0) {
                minDistance = currDistance;
                target = player;
            }
        }

        return target;
    }
}