package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.database.firebase.callback.OnAddUserCallback;
import ch.epfl.sdp.database.firebase.callback.OnValueReadyCallback;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.room.LeaderboardEntity;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.leaderboard.LeaderboardViewModel;

public class CommonFirestoreDatabaseAPI implements CommonDatabaseAPI {
    private static final String USER_COLLECTION_NAME = "AllUsers";
    private static final int NUMBER_OF_PLAYERS_IN_LOBBY = 10;
    private static final String LOBBY_COLLECTION_NAME = "Lobbies";
    private static final String PLAYERS_COLLECTION_NAME = "Players";

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    public void syncCloudFirebaseToRoom(LeaderboardViewModel leaderboardViewModel) {
        FirebaseFirestore.getInstance().collection("Users")
                .orderBy("healthPoints", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Player player = documentSnapshot.toObject(Player.class);
                        LeaderboardEntity user = new LeaderboardEntity(player.getEmail(), player.getUsername(), player.getScore());
                        leaderboardViewModel.insert(user);
                    }
                });
    }

    @Override
    public void addUser(UserForFirebase userForFirebase, OnAddUserCallback onAddUserCallback) {
        firebaseFirestore.collection(USER_COLLECTION_NAME)
                .document(userForFirebase.getEmail())
                .set(userForFirebase)
                .addOnSuccessListener(aVoid -> onAddUserCallback.finish())
                .addOnFailureListener(e -> onAddUserCallback.error(e));
    }

    @Override
    public void fetchUser(String email, OnValueReadyCallback<UserForFirebase> onValueReadyCallback) {
        firebaseFirestore.collection(USER_COLLECTION_NAME).document(email).get()
                .addOnSuccessListener(documentSnapshot -> onValueReadyCallback.finish(documentSnapshot.toObject(UserForFirebase.class)))
                .addOnFailureListener(e -> onValueReadyCallback.error(e));
    }

    @Override
    public void joinLobby(PlayerForFirebase playerForFirebase, OnValueReadyCallback<Boolean> onValueReadyCallback) {
        CollectionReference collectionReference = firebaseFirestore.collection(LOBBY_COLLECTION_NAME);
        collectionReference.whereLessThan("count", NUMBER_OF_PLAYERS_IN_LOBBY).limit(1).get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        onValueReadyCallback.error(task.getException());
                    } else {
                        if (task.getResult().isEmpty())
                            createNewLobbyAndRegister(collectionReference.document(), playerForFirebase, onValueReadyCallback);
                        else
                            registerToLobby(task.getResult().iterator().next(), playerForFirebase, onValueReadyCallback);
                    }
                });
    }

    private void createNewLobbyAndRegister(DocumentReference docRefLobby, PlayerForFirebase playerForFirebase, OnValueReadyCallback<Boolean> onValueReadyCallback) {
        DocumentReference docRefPlayer = docRefLobby.collection(PLAYERS_COLLECTION_NAME).document(playerForFirebase.getEmail());

        Map<String, Object> data = new HashMap<>();
        data.put("count", 1);
        data.put("startGame", false);

        docRefPlayer.set(playerForFirebase);
        docRefLobby.set(data, SetOptions.merge());

        onValueReadyCallback.finish(true);
    }

    private void registerToLobby(QueryDocumentSnapshot document, PlayerForFirebase playerForFirebase, OnValueReadyCallback<Boolean> onValueReadyCallback) {
        DocumentReference docRefLobby = document.getReference();
        DocumentReference docRefPlayer = docRefLobby.collection(PLAYERS_COLLECTION_NAME).document(playerForFirebase.getEmail());

        long newCount = document.getLong("count") + 1;
        docRefPlayer.set(playerForFirebase);
        docRefLobby.update("count", newCount);

        onValueReadyCallback.finish(false);
    }

    @Override
    public void fetchPlayers() {

    }
}
