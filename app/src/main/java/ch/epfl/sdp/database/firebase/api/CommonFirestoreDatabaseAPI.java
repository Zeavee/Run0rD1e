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
import ch.epfl.sdp.database.firebase.utils.CustumResult;
import ch.epfl.sdp.database.firebase.utils.OnValueReadyCallback;
import ch.epfl.sdp.database.room.LeaderboardEntity;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.leaderboard.LeaderboardViewModel;

public class CommonFirestoreDatabaseAPI implements CommonDatabaseAPI {

    protected static final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    public void syncCloudFirebaseToRoom(LeaderboardViewModel leaderboardViewModel) {
        firebaseFirestore.collection(PlayerManager.USER_COLLECTION_NAME)
                .orderBy("score", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        UserForFirebase player = documentSnapshot.toObject(UserForFirebase.class);
                        LeaderboardEntity user = new LeaderboardEntity(player.getEmail(), player.getUsername(), player.getScore());
                        leaderboardViewModel.insert(user);
                    }
                });
    }

    @Override
    public void addUser(UserForFirebase userForFirebase, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.USER_COLLECTION_NAME).document(userForFirebase.getEmail()).set(userForFirebase)
                .addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustumResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustumResult<>(null, false, e)));
    }

    @Override
    public void fetchUser(String email, OnValueReadyCallback<CustumResult<UserForFirebase>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.USER_COLLECTION_NAME).document(email).get()
                .addOnSuccessListener(documentSnapshot -> {
                    UserForFirebase userForFirebase = documentSnapshot.toObject(UserForFirebase.class);
                    onValueReadyCallback.finish(new CustumResult<>(userForFirebase, true, null));
                })
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustumResult<>(null, false, e)));
    }

    @Override
    public void selectLobby(OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback) {
        CollectionReference lobbyRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME);
        lobbyRef.whereLessThan("count", PlayerManager.NUMBER_OF_PLAYERS_IN_LOBBY).limit(1).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(queryDocumentSnapshots.isEmpty()) {
                        PlayerManager.setLobbyDocumentName(lobbyRef.document().getId());
                        PlayerManager.setIsServer(true);
                        PlayerManager.setNumPlayersBeforeJoin(0);
                    } else {
                        QueryDocumentSnapshot doc = queryDocumentSnapshots.iterator().next();
                        PlayerManager.setLobbyDocumentName(doc.getId());
                        PlayerManager.setIsServer(false);
                        PlayerManager.setNumPlayersBeforeJoin(doc.getLong("count"));
                    }
                    onValueReadyCallback.finish(new CustumResult<>(null, true, null));

                }).addOnFailureListener(e -> onValueReadyCallback.finish(new CustumResult<>(null, false, e)));
    }

    @Override
    public void registerToLobby(PlayerForFirebase playerForFirebase, Map<String, Object> data, OnValueReadyCallback<CustumResult<Void>> onValueReadyCallback){
        DocumentReference docRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(PlayerManager.getLobbyDocumentName());
        docRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail()).set(playerForFirebase)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        docRef.set(data, SetOptions.merge()).addOnCompleteListener(task1 -> onValueReadyCallback.finish(new CustumResult<>(null, true, null)));
                    }
                });
    }

    @Override
    public void fetchPlayers(OnValueReadyCallback<CustumResult<List<PlayerForFirebase>>> onValueReadyCallback) {
        DocumentReference docRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(PlayerManager.getLobbyDocumentName());
        docRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<PlayerForFirebase> playerForFirebases = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        playerForFirebases.add(document.toObject(PlayerForFirebase.class));
                    }
                    onValueReadyCallback.finish(new CustumResult<>(playerForFirebases, true, null));
                }).addOnFailureListener(e -> onValueReadyCallback.finish(new CustumResult<>(null, false, e)));
    }

}
