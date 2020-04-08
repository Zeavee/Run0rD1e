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

import ch.epfl.sdp.db.LeaderboardEntity;
import ch.epfl.sdp.db.LeaderoardViewModel;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;

public class FirestoreUserData implements UserDataController {
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
    public void storeUser(Player player) {
        FirebaseFirestore.getInstance().collection("Users").document(player.getEmail()).set(player);
    }

    @Override
    public void joinLobby(Player player) {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection(PlayerManager.LOBBY_PATH);
        collectionReference
            .whereLessThan("count", PlayerManager.NUMBER_OF_PLAYERS_IN_Lobby)
            .limit(1)
            .get()
            .addOnCompleteListener(task -> {
                Map<String, Object> data = new HashMap<>();
                if (task.isSuccessful()) {
                    if (task.getResult().isEmpty()) {
                        DocumentReference docRefLobby = collectionReference.document();
                        DocumentReference docRefPlayer = docRefLobby.collection(PlayerManager.PLAYERS_PATH).document(player.getEmail());
                        data.put("count", 1);
                        data.put("startGame", false);
                        docRefPlayer.set(player);
                        docRefLobby.set(data, SetOptions.merge());
                    } else{
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            DocumentReference docRefLobby = document.getReference();
                            DocumentReference docRefPlayer = docRefLobby.collection(PlayerManager.PLAYERS_PATH).document(player.getEmail());
                            long newCount = document.getLong("count") + 1;

                            docRefPlayer.set(player);
                            docRefLobby.update("count", newCount);
                        }
                    }
                } else {
                    Log.d("FAILURE", "Error getting documents: ", task.getException());
                }
            });
    }


}
