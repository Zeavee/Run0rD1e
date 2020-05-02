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
    public static final int NUMBER_OF_PLAYERS_IN_LOBBY = 2;
    public static final String USER_COLLECTION_NAME = "AllUsers";
    public static final String LOBBY_COLLECTION_NAME = "LobbyRaphael";
    public static final String PLAYER_COLLECTION_NAME = "Players";
    public static final String ENEMY_COLLECTION_NAME = "Enemies";
    public static final String ITEM_COLLECTION_NAME = "Items";
    public static final String USED_ITEM_COLLECTION_NAME = "UsedItems";

    private String lobbyDocumentName;
    private long numPlayersBeforeJoin;
    private boolean isServer;

    /**
     * The player representing the user in the game.
     */
    private Player currentUser;

    private Map<String, Player> playersMap = new HashMap<String, Player>();

    private List<Player> playersWaitingItems = new ArrayList<Player>();

    private List<Player> playersWaitingHealthPoint = new ArrayList<Player>();

    private static final PlayerManager instance = new PlayerManager();

    public static PlayerManager getInstance() {
        return instance;
    }

    public void updatePlayersPosition(List<PlayerForFirebase> players){
        for (PlayerForFirebase player: players) {
            playersMap.get(player.getEmail()).setLocation(player.getLocation());
        }
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

    public long getNumPlayersBeforeJoin() {
        return numPlayersBeforeJoin;
    }

    public void setNumPlayersBeforeJoin(long numPlayersBeforeJoin) {
        this.numPlayersBeforeJoin = numPlayersBeforeJoin;
    }

    public boolean isServer() {
        return isServer;
    }

    public void setIsServer(boolean isServer) {
        this.isServer = isServer;
    }

    /**
     * Add the specified player to the player manager. The player will stay until it is removed by
     * removePlayer() or removeAll().
     *
     * @param player A player to be stored into the player manager.
     */
    public void addPlayer(Player player) {
        playersMap.put(player.getEmail(),player);
    }

    /**
     * Remove the specified player from the player manager if it exists.
     *
     * @param player A player to be removed from the player manager.
     */
    public void removePlayer(Player player) {
        playersMap.remove(player.getEmail());
    }

    /**
     * Gets a list of all players in the player manager.
     *
     * @return A list of all players in the player manager
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(playersMap.values());
    }

    public Map<String, Player> getPlayersMap(){
        return playersMap;
    }

    /**
     * Set a list of all players in the player manager
     *
     * @param players A list of players to be added
     */
    public void setPlayers(List<Player> players) {

        playersMap.clear();
        for (Player player : players){
            playersMap.put(player.getEmail(),player);
        }
    }

    public List<Player> getPlayersWaitingItems() {
        return playersWaitingItems;
    }

    public void addPlayerWaitingItems(Player player) {
        if(!currentUser.getEmail().equals(player.getEmail())) {
            playersWaitingItems.add(player);
        }
    }

    public List<Player> getPlayersWaitingHealthPoint() {
        return playersWaitingHealthPoint;
    }

    public void addPlayerWaitingHealth(Player player) {
        if(!currentUser.getEmail().equals(player.getEmail())) {
            playersWaitingHealthPoint.add(player);
        }
    }

    public void clearPlayerWaitingItems(){
        playersWaitingItems.clear();
    }

    public void clearPlayerWaitingHealthPoint(){
        playersWaitingHealthPoint.clear();
    }

    /**
     * Remove all the players in the player manager.
     */
    public void removeAll() {
        playersMap.clear();
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
     * player alive it returns null.
     */
    public Player selectClosestPlayer(CartesianPoint position) {
        Player target = null;
        double minDistance = Double.MAX_VALUE;
        double currDistance;

        for (Player player : playersMap.values()) {
            currDistance = player.getPosition().distanceFrom(position) - player.getAoeRadius();
            if (currDistance < minDistance && player.isAlive()) {
                minDistance = currDistance;
                target = player;
            }
        }

        return target;
    }
}