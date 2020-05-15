package ch.epfl.sdp.database.firebase;

import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.api.ClientDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;

public class ClientMockDatabaseAPI implements ClientDatabaseAPI {

    @Override
    public void setLobbyRef(String lobbyName) {

    }

    @Override
    public void listenToGameStart(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {

    }

    @Override
    public void addCollectionListener(Object entityType, OnValueReadyCallback<CustomResult<List<Object>>> onValueReadyCallback) {

    }

    @Override
    public void addUserItemListener(OnValueReadyCallback<CustomResult<Map<String, Integer>>> onValueReadyCallback) {

    }

    @Override
    public void addGameAreaListener(OnValueReadyCallback<CustomResult<String>> onValueReadyCallback) {

    }

    @Override
    public void sendUserPosition(PlayerForFirebase playerForFirebase) {

    }

    @Override
    public void sendUsedItems(ItemsForFirebase itemsForFirebase) {

    }
}
