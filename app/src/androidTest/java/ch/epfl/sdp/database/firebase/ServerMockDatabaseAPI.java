package ch.epfl.sdp.database.firebase;

import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;

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
    public void sendItemBoxes(List<ItemBoxForFirebase> itemBoxForFirebaseList) {

    }

    @Override
    public void sendPlayersHealth(List<PlayerForFirebase> playerForFirebases) {

    }

    @Override
    public void sendPlayersItems(Map<String, ItemsForFirebase> emailsItemsMap) {

    }

    @Override
    public void updatePlayersScore(String scoreType, Map<String, Integer> emailsScoreMap) {

    }

    @Override
    public void addUsedItemsListener(OnValueReadyCallback<CustomResult<Map<String, ItemsForFirebase>>> onValueReadyCallback) {

    }

    @Override
    public void addPlayersListener(OnValueReadyCallback<CustomResult<List<PlayerForFirebase>>> onValueReadyCallback) {

    }
}