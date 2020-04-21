package ch.epfl.sdp.database.firebase.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Map;

import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.room.LeaderboardEntity;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.leaderboard.LeaderboardViewModel;

public class CommonFirestoreDatabaseAPI implements CommonDatabaseAPI {

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
    public void addUser(UserForFirebase userForFirebase, OnValueReadyCallback<Task<Void>> onValueReadyCallback) {
        PlayerManager.USER_COLLECTION_REF
                .document(userForFirebase.getEmail())
                .set(userForFirebase)
                .addOnCompleteListener(task -> onValueReadyCallback.finish(task));
    }

    @Override
    public void fetchUser(String email, OnValueReadyCallback<Task<DocumentSnapshot>> onValueReadyCallback) {
        PlayerManager.USER_COLLECTION_REF.document(email).get()
                .addOnCompleteListener(task -> onValueReadyCallback.finish(task));
    }

    @Override
    public void selectLobby(OnValueReadyCallback<Task<QuerySnapshot>> onValueReadyCallback) {
        PlayerManager.LOBBY_COLLECTION_REF.whereLessThan("count", PlayerManager.NUMBER_OF_PLAYERS_IN_LOBBY).limit(1).get()
                .addOnCompleteListener(task -> onValueReadyCallback.finish(task));
    }

    @Override
    public void registerToLobby(PlayerForFirebase playerForFirebase, Map<String, Object> data, OnValueReadyCallback<Task<Void>> onValueReadyCallback) {
        PlayerManager.getLobby_doc_ref().collection(PlayerManager.PLAYERS_COLLECTION_NAME).document(playerForFirebase.getEmail())
                .set(playerForFirebase)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        PlayerManager.getLobby_doc_ref().set(data, SetOptions.merge()).addOnCompleteListener(task1 -> onValueReadyCallback.finish(task1));
                    }
                });
    }

    @Override
    public void fetchPlayers(OnValueReadyCallback<Task<QuerySnapshot>> onValueReadyCallback) {
        PlayerManager.getLobby_doc_ref().collection(PlayerManager.PLAYERS_COLLECTION_NAME).get()
                .addOnCompleteListener(task -> onValueReadyCallback.finish(task));
    }

}
