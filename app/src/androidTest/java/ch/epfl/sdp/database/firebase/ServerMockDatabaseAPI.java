package ch.epfl.sdp.database.firebase;

import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.firebase.entityForFirebase.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.UserForFirebase;
import ch.epfl.sdp.utils.CustomResult;
import ch.epfl.sdp.utils.OnValueReadyCallback;
import ch.epfl.sdp.geometry.Area;

public class ServerMockDatabaseAPI implements ServerDatabaseAPI {
    @Override
    public void setLobbyRef(String lobbyName) {

    }

    @Override
    public void listenToNumOfPlayers(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {

    }

    @Override
    public void startGame(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {

    }

    @Override
    public void fetchGeneralScoreForPlayers(List<String> playerEmailList, OnValueReadyCallback<CustomResult<List<UserForFirebase>>> onValueReadyCallback) {

    }

    @Override
    public void sendEnemies(List<EnemyForFirebase> enemies) {

    }

    @Override
    public <T> void addCollectionListener(Class<T> tClass, String collectionName, OnValueReadyCallback<CustomResult<List<T>>> onValueReadyCallback) {

    }

    @Override
    public void sendItemBoxes(List<ItemBoxForFirebase> itemBoxForFirebaseList) {

    }

    @Override
    public void sendPlayersStatus(List<PlayerForFirebase> playerForFirebaseList) {

    }

    @Override
    public void sendPlayersItems(Map<String, ItemsForFirebase> emailsItemsMap) {

    }

    @Override
    public void addUsedItemsListener(OnValueReadyCallback<CustomResult<Map<String, ItemsForFirebase>>> onValueReadyCallback) {

    }

    @Override
    public void cleanListeners() {

    }

    @Override
    public void sendServerAliveSignal(long signal) {

    }

    @Override
    public void updatePlayersCurrentScore(Map<String, Integer> emailsScoreMap) {

    }

    @Override
    public void sendGameArea(Area gameArea) {

    }
}