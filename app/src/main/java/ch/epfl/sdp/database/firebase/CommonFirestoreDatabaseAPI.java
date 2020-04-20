package ch.epfl.sdp.database.firebase;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.database.room.LeaderboardEntity;
import ch.epfl.sdp.database.room.LeaderoardViewModel;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;

public class CommonFirestoreDatabaseAPI implements CommonDatabaseAPI {
    private static final String USER_COLLECTION_NAME = "AllUsers";
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    public void syncCloudFirebaseToRoom(LeaderoardViewModel leaderoardViewModel) {
        FirebaseFirestore.getInstance().collection("Users")
                .orderBy("healthPoints", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Player player = documentSnapshot.toObject(Player.class);
                        LeaderboardEntity user = new LeaderboardEntity(player.getEmail(), player.getUsername(), player.getScore());
                        leaderoardViewModel.insert(user);
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
    public void joinLobby(Player player) {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection(PlayerManager.LOBBY_PATH);
        collectionReference
                .whereLessThan("count", PlayerManager.NUMBER_OF_PLAYERS_IN_Lobby)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.d("FAILURE", "Error getting documents: ", task.getException());
                        return;
                    }

                    if (task.getResult().isEmpty())
                        createNewLobby(collectionReference.document(), player);
                    else
                        addPlayerToLobby(task.getResult().iterator().next(), player);
                });
    }

    @Override
    public void fetchPlayers() {

    }

    private void createNewLobby(DocumentReference docRefLobby, Player player) {
        Map<String, Object> data = new HashMap<>();
        DocumentReference docRefPlayer = docRefLobby.collection(PlayerManager.PLAYERS_PATH).document(player.getEmail());
        data.put("count", 1);
        data.put("startGame", false);
        docRefPlayer.set(player);
        docRefLobby.set(data, SetOptions.merge());
    }

    private void addPlayerToLobby(QueryDocumentSnapshot document, Player player) {
        DocumentReference docRefLobby = document.getReference();
        DocumentReference docRefPlayer = docRefLobby.collection(PlayerManager.PLAYERS_PATH).document(player.getEmail());
        long newCount = document.getLong("count") + 1;

        docRefPlayer.set(player);
        docRefLobby.update("count", newCount);
    }
}
