package ch.epfl.sdp.database.firebase.api;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ch.epfl.sdp.database.firebase.entityForFirebase.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.UserForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;

public class CommonFirestoreDatabaseAPI implements CommonDatabaseAPI {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private static final PlayerManager playerManager = PlayerManager.getInstance();
    private final List<ListenerRegistration> listeners = new ArrayList<>();

    @Override
    public void addUser(UserForFirebase userForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.USER_COLLECTION_NAME).document(userForFirebase.getEmail()).set(userForFirebase)
                .addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void fetchUser(String email, OnValueReadyCallback<CustomResult<UserForFirebase>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.USER_COLLECTION_NAME).document(email).get()
                .addOnSuccessListener(documentSnapshot -> {
                    UserForFirebase userForFirebase = documentSnapshot.toObject(UserForFirebase.class);
                    onValueReadyCallback.finish(new CustomResult<>(userForFirebase, true, null));
                }).addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void selectLobby(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        CollectionReference lobbyRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME);
        lobbyRef.whereLessThan("players", PlayerManager.NUMBER_OF_PLAYERS_IN_LOBBY).limit(1).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(queryDocumentSnapshots.isEmpty()){
                        setPlayerManager(lobbyRef.document().getId(), false, 0);
                        Map<String, Object> data = new HashMap<>();
                        data.put("players", 0);
                        data.put("signal", 0);
                        data.put("startGame", false);
                        lobbyRef.document(playerManager.getLobbyDocumentName()).set(data, SetOptions.merge());
                        onValueReadyCallback.finish(new CustomResult<>(null, true, null));
                    }else {
                        QueryDocumentSnapshot doc = queryDocumentSnapshots.iterator().next();
                        fetchPlayers(doc.getId(), res -> {
                            if (res.isSuccessful()) {
                                List<PlayerForFirebase> playersForFirebase = res.getResult();
                                for (PlayerForFirebase playerForFirebase : playersForFirebase) {
                                    if (playerManager.getCurrentUser().getEmail().equals(playerForFirebase.getEmail())) {
                                        playerManager.setInLobby(true);
                                        break;
                                    }
                                }

                                if (queryDocumentSnapshots.size() == PlayerManager.NUMBER_OF_PLAYERS_IN_LOBBY - 1 && !playerManager.isInLobby()) {
                                    setPlayerManager(doc.getId(), true, doc.getLong("players"));
                                } else {
                                    setPlayerManager(doc.getId(), false, doc.getLong("players"));
                                }

                                onValueReadyCallback.finish(new CustomResult<>(null, true, null));
                            }
                        });
                    }
                }).addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    private void setPlayerManager(String lobby, boolean isServer, long players_count){
        playerManager.setLobbyDocumentName(lobby);
        playerManager.setIsServer(isServer);
        playerManager.setNumPlayersInLobby(players_count);
    }

    @Override
    public void registerToLobby(PlayerForFirebase playerForFirebase, Map<String, Object> data, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        if(playerManager.getLobbyDocumentName() != "") {
            DocumentReference docRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(playerManager.getLobbyDocumentName());
            docRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail()).set(playerForFirebase)
                    .addOnSuccessListener(aVoid -> docRef.set(data, SetOptions.merge())
                            .addOnSuccessListener(aVoid1 -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                            .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e))))
                    .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
        }
    }

    @Override
    public void fetchPlayers(String lobbyName, OnValueReadyCallback<CustomResult<List<PlayerForFirebase>>> onValueReadyCallback) {
        if(lobbyName != "") {
            firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(lobbyName).collection(PlayerManager.PLAYER_COLLECTION_NAME).get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<PlayerForFirebase> playerForFirebases = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            playerForFirebases.add(document.toObject(PlayerForFirebase.class));
                        }
                        onValueReadyCallback.finish(new CustomResult<>(playerForFirebases, true, null));
                    }).addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
        }else{
            Log.d("Database", "No lobby ID.");
        }
    }

    @Override
    public void generalGameScoreListener(OnValueReadyCallback<CustomResult<List<UserForFirebase>>> onValueReadyCallback) {
        ListenerRegistration listenerRegistration = firebaseFirestore.collection(PlayerManager.USER_COLLECTION_NAME).addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                onValueReadyCallback.finish(new CustomResult<>(null, false, e));
            } else {
                List<UserForFirebase> userForFirebaseList = new ArrayList<>();
                for (DocumentChange dc : Objects.requireNonNull(queryDocumentSnapshots).getDocumentChanges()) {
                    userForFirebaseList.add(dc.getDocument().toObject(UserForFirebase.class));
                }
                onValueReadyCallback.finish(new CustomResult<>(userForFirebaseList, true, null));
            }
        });
        listeners.add(listenerRegistration);
    }

    @Override
    public void sendUserPosition(PlayerForFirebase playerForFirebase) {
        DocumentReference lobbyRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(PlayerManager.getInstance().getLobbyDocumentName());
        lobbyRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail()).update("geoPointForFirebase", playerForFirebase.getGeoPointForFirebase());
    }

    @Override
    public void cleanListeners() {
        FireStoreDatabaseAPI.cleanListeners(listeners);
    }

    @Override
    public void updatePlayerGeneralScore(Player player) {
        WriteBatch batch = firebaseFirestore.batch();
        DocumentReference docRef = firebaseFirestore.collection(PlayerManager.USER_COLLECTION_NAME).document(player.getEmail());
        batch.update(docRef, "generalScore", player.getGeneralScore());
    }
}