package ch.epfl.sdp.database.firebase.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.GeoPointForFirebase;
import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entity.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;

public class ServerMockDatabaseAPI implements ServerDatabaseAPI {
    public Map<String, PlayerForFirebase> playerForFirebaseMap = new HashMap<String, PlayerForFirebase>();
    public Map<String, ItemsForFirebase> usedItems = new HashMap<>();
    public Map<String, ItemsForFirebase> items = new HashMap<>();

    public ServerMockDatabaseAPI() {

    }

    public void hardCodedInit(){
        PlayerForFirebase playerForFirebase0 = new PlayerForFirebase();

        playerForFirebase0.setUsername("server");
        playerForFirebase0.setEmail("server@gmail.com");
        playerForFirebase0.setGeoPointForFirebase(new GeoPointForFirebase(22,22));
        playerForFirebase0.setAoeRadius(22.0);
        playerForFirebase0.setHealthPoints(20.0);
        playerForFirebase0.setCurrentGameScore(0);

        PlayerForFirebase playerForFirebase1 = new PlayerForFirebase();

        playerForFirebase1.setUsername("client");
        playerForFirebase1.setEmail("client@gmail.com");
        playerForFirebase1.setGeoPointForFirebase(new GeoPointForFirebase(22,22));
        playerForFirebase1.setAoeRadius(22.0);
        playerForFirebase1.setHealthPoints(20.0);
        playerForFirebase1.setCurrentGameScore(0);

        playerForFirebaseMap.put(playerForFirebase0.getEmail(), playerForFirebase0);
        playerForFirebaseMap.put(playerForFirebase1.getEmail(), playerForFirebase1);

        Map<String, Integer> itemsMap = new HashMap<>();
        itemsMap.put("Healthpack 10", 2);
        ItemsForFirebase itemsForFirebase = new ItemsForFirebase(itemsMap, new Date(System.currentTimeMillis()));
        usedItems.put("server@gmail.com", itemsForFirebase);
    }

    @Override
    public void setLobbyRef(String lobbyName) {

    }

    @Override
    public void listenToNumOfPlayers(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        onValueReadyCallback.finish(new CustomResult<>(null, true, null));
    }

    @Override
    public void startGame(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        onValueReadyCallback.finish(new CustomResult<>(null, true, null));
    }

    @Override
    public void fetchPlayers(OnValueReadyCallback<CustomResult<List<PlayerForFirebase>>> onValueReadyCallback) {
        onValueReadyCallback.finish(new CustomResult<>(getPlayerForFirebaseList(), true, null));
    }

    @Override
    public void sendEnemies(List<EnemyForFirebase> enemies) {

    }

    @Override
    public void sendItemBoxes(List<ItemBoxForFirebase> itemBoxForFirebaseList) {

    }

    @Override
    public void sendPlayersHealth(List<PlayerForFirebase> playersForFirebase) {
        for (PlayerForFirebase playerForFirebase: playersForFirebase) {
            playerForFirebaseMap.get(playerForFirebase.getEmail())
                    .setHealthPoints(playerForFirebase.getHealthPoints());
        }
    }

    @Override
    public void sendPlayersItems(Map<String, ItemsForFirebase> emailsItemsMap) {
        for (Map.Entry<String, ItemsForFirebase> item: emailsItemsMap.entrySet()) {
            items.put(item.getKey(),item.getValue());
        }
    }

    @Override
    public void addUsedItemsListener(OnValueReadyCallback<CustomResult<Map<String, ItemsForFirebase>>> onValueReadyCallback) {
        onValueReadyCallback.finish(new CustomResult<>(usedItems, true, null));
    }

    @Override
    public void addPlayersPositionListener(OnValueReadyCallback<CustomResult<List<PlayerForFirebase>>> onPlayersPositionCallback) {
        onPlayersPositionCallback.finish(new CustomResult<>(getPlayerForFirebaseList(), true, null));
    }

    public List<PlayerForFirebase> getPlayerForFirebaseList(){
        return new ArrayList<>(playerForFirebaseMap.values());
    }

    public void addPlayerForFirebase(PlayerForFirebase playerForFirebase){
        playerForFirebaseMap.put(playerForFirebase.getEmail(), playerForFirebase);
    }
}
