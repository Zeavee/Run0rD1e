package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.room.LeaderboardEntity;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.leaderboard.LeaderboardViewModel;

public class CommonFirestoreDatabaseAPI implements CommonDatabaseAPI {

    protected static final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    protected static final PlayerManager playerManager = PlayerManager.getInstance();

    @Override
    public void syncCloudFirebaseToRoom(LeaderboardViewModel leaderboardViewModel) {
        firebaseFirestore.collection(playerManager.USER_COLLECTION_NAME)
                .orderBy("score", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        UserForFirebase player = documentSnapshot.toObject(UserForFirebase.class);
                        LeaderboardEntity user = new LeaderboardEntity(player.getEmail(), player.getUsername(), player.getGeneralScore());
                        leaderboardViewModel.insertToLeaderboard(user);
                    }
                });
    }

    @Override
    public void addUser(UserForFirebase userForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        firebaseFirestore.collection(playerManager.USER_COLLECTION_NAME).document(userForFirebase.getEmail()).set(userForFirebase)
                .addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void fetchUser(String email, OnValueReadyCallback<CustomResult<UserForFirebase>> onValueReadyCallback) {
        firebaseFirestore.collection(playerManager.USER_COLLECTION_NAME).document(email).get()
                .addOnSuccessListener(documentSnapshot -> {
                    UserForFirebase userForFirebase = documentSnapshot.toObject(UserForFirebase.class);
                    onValueReadyCallback.finish(new CustomResult<>(userForFirebase, true, null));
                }).addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void selectLobby(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        CollectionReference lobbyRef = firebaseFirestore.collection(playerManager.LOBBY_COLLECTION_NAME);
        lobbyRef.whereLessThan("count", playerManager.NUMBER_OF_PLAYERS_IN_LOBBY).limit(1).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        playerManager.setLobbyDocumentName(lobbyRef.document().getId());
                        playerManager.setIsServer(true);
                        playerManager.setNumPlayersBeforeJoin(0);
                    } else {
                        QueryDocumentSnapshot doc = queryDocumentSnapshots.iterator().next();
                        playerManager.setLobbyDocumentName(doc.getId());
                        playerManager.setIsServer(false);
                        playerManager.setNumPlayersBeforeJoin(doc.getLong("count"));
                    }
                    onValueReadyCallback.finish(new CustomResult<>(null, true, null));
                }).addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void registerToLobby(PlayerForFirebase playerForFirebase, Map<String, Object> data, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        DocumentReference docRef = firebaseFirestore.collection(playerManager.LOBBY_COLLECTION_NAME).document(playerManager.getLobbyDocumentName());
        docRef.collection(playerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail()).set(playerForFirebase)
                .addOnSuccessListener(aVoid -> docRef.set(data, SetOptions.merge())
                        .addOnSuccessListener(aVoid1 -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                        .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e))))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void fetchPlayers(OnValueReadyCallback<CustomResult<List<PlayerForFirebase>>> onValueReadyCallback) {
        DocumentReference docRef = firebaseFirestore.collection(playerManager.LOBBY_COLLECTION_NAME).document(playerManager.getLobbyDocumentName());
        docRef.collection(playerManager.PLAYER_COLLECTION_NAME).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<PlayerForFirebase> playerForFirebases = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        playerForFirebases.add(document.toObject(PlayerForFirebase.class));
                    }
                    onValueReadyCallback.finish(new CustomResult<>(playerForFirebases, true, null));
                }).addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void updateLocation(PlayerForFirebase playerForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        firebaseFirestore.collection(playerManager.LOBBY_COLLECTION_NAME).document(playerManager.getLobbyDocumentName()).collection(playerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail())
                .update("location", playerForFirebase.getLocation())
                .addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }
}