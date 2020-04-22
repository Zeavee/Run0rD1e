package ch.epfl.sdp.database;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.db.AppViewModel;
import ch.epfl.sdp.db.PlayerEntity;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirestoreUserData implements UserDataController {
    @Override
    public void syncCloudFirebaseToRoom(AppViewModel appViewModel, String collectionName) {
        FirebaseFirestore.getInstance().collection(collectionName)
            .orderBy("healthPoints", Query.Direction.DESCENDING)
            .addSnapshotListener((queryDocumentSnapshots, e) -> {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Player player = documentSnapshot.toObject(Player.class);
                    PlayerEntity playerEntity = new PlayerEntity(player.getEmail(), player.getUsername(), player.getHealthPoints());
                    appViewModel.insertToLeaderboard(playerEntity);
                }
            });
    }

    @Override
    public void storeUser(String collectionName, Player player) {
        FirebaseFirestore.getInstance().collection(collectionName).document(player.getEmail()).set(player);
    }

    @Override
    public void getLobby(String collectionName) {
        FirebaseFirestore.getInstance().collection(collectionName)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Player player = document.toObject(Player.class);
                        PlayerManager.getPlayers().add(player);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            });
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
