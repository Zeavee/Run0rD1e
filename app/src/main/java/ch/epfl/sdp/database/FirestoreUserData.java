package ch.epfl.sdp.database;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.db.LeaderboardEntity;
import ch.epfl.sdp.db.LeaderoardViewModel;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.map.MapsActivity;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirestoreUserData implements UserDataController, Updatable {
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
    public void storeUser(String collectionName, Player player) {
        FirebaseFirestore.getInstance().collection(collectionName).document(player.getEmail()).set(player, SetOptions.merge());
    }

    @Override
    public void getLobby(String collectionName) {
        FirebaseFirestore.getInstance().collection(collectionName)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Player player = document.toObject(Player.class);
                        MapsActivity.playerManager.addPlayer(player);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            });
    }

    @Override
    public void update() {

    }
}
